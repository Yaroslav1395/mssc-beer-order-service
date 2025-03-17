package sakhno.sfg.beer.order.service.services.order;

import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.repositories.CustomerRepository;
import sakhno.sfg.beer.order.service.web.mappers.BeerOrderMapper;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final ApplicationEventPublisher publisher;

    public BeerOrderServiceImpl(BeerOrderRepository beerOrderRepository,
                                CustomerRepository customerRepository,
                                BeerOrderMapper beerOrderMapper, ApplicationEventPublisher publisher) {
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.beerOrderMapper = beerOrderMapper;
        this.publisher = publisher;
    }

    /**
     * Метод позволяет найти список заказов заказчика по id
     * @param customerId - id заказчика
     * @param pageable - параметры пагинации
     * @return - список заказов с пагинацией
     */
    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Page<BeerOrderEntity> beerOrderPage =
                    beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

            return new BeerOrderPagedList(beerOrderPage
                    .stream()
                    .map(beerOrderMapper::beerOrderToDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    beerOrderPage.getPageable().getPageNumber(),
                    beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());
        } else {
            return null;
        }
    }

    /**
     * Метод позволяет создать новый заказ
     * @param customerId - id заказчика
     * @param beerOrderDto - заказ пива
     * @return - новый заказ пива
     */
    @Transactional
    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
        Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            BeerOrderEntity beerOrderEntity = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrderEntity.setId(null); //should not be set by outside client
            beerOrderEntity.setCustomer(customerOptional.get());
            beerOrderEntity.setOrderStatus(BeerOrderStatusEnum.NEW);
            Set<BeerOrderLineEntity> beerOrderLineEntity = beerOrderEntity.getBeerOrderLines();
            beerOrderEntity.setBeerOrderLines(null);
            BeerOrderEntity beerOrder = beerOrderRepository.saveAndFlush(beerOrderEntity);
            beerOrderLineEntity.forEach(line -> line.setBeerOrder(beerOrder));

            BeerOrderEntity savedBeerOrderEntity = beerOrderRepository.saveAndFlush(beerOrder);

            log.debug("Saved Beer Order: " + beerOrderEntity.getId());

            //todo impl
          //  publisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));

            return beerOrderMapper.beerOrderToDto(savedBeerOrderEntity);
        }
        //todo add exception type
        throw new RuntimeException("Customer Not Found");
    }

    /**
     * Метод позволяет получить заказ по его id и id заказчика
     * @param customerId - id заказчика
     * @param orderId - id заказа
     * @return - заказ
     */
    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    /**
     * Метод позволяет найти заказ по id и id заказчика с изменением его статуса на "Просмотрен".
     * @param customerId - id заказчика
     * @param orderId - id заказа
     */
    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrderEntity beerOrderEntity = getOrder(customerId, orderId);
        beerOrderEntity.setOrderStatus(BeerOrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrderEntity);
    }

    /**
     * Метод позволяет найти заказ по id заказчика и id заказа
     * @param customerId - id заказчика
     * @param orderId - id заказа
     * @return - заказ
     */
    private BeerOrderEntity getOrder(UUID customerId, UUID orderId){
        Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(orderId);

            if(beerOrderOptional.isPresent()){
                BeerOrderEntity beerOrderEntity = beerOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if(beerOrderEntity.getCustomer().getId().equals(customerId)){
                    return beerOrderEntity;
                }
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}
