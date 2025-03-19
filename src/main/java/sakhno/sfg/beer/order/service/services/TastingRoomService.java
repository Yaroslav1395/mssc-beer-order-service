package sakhno.sfg.beer.order.service.services;

import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.repositories.CustomerRepository;
import sakhno.sfg.beer.order.service.services.order.BeerOrderService;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    private final BeerOrderRepository beerOrderRepository;
    private final List<String> beerUpcs = new ArrayList<>(3);
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final String TASTING_ROOM = "Tasting Room";

    public TastingRoomService(CustomerRepository customerRepository, BeerOrderService beerOrderService,
                              BeerOrderRepository beerOrderRepository) {
        this.customerRepository = customerRepository;
        this.beerOrderService = beerOrderService;
        this.beerOrderRepository = beerOrderRepository;

        beerUpcs.add(TastingRoomService.BEER_1_UPC);
        beerUpcs.add(TastingRoomService.BEER_2_UPC);
        beerUpcs.add(TastingRoomService.BEER_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeTastingRoomOrder(){
        log.info("Отработка метода placeTastingRoomOrder");
        List<CustomerEntity> customerEntityList = customerRepository.findAllByCustomerNameLike(TastingRoomService.TASTING_ROOM);
        if (customerEntityList.size() == 1){ //should be just one
            doPlaceOrder(customerEntityList.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
            customerEntityList.forEach(customerEntity -> log.error(customerEntity.toString()));
        }
    }

    private void doPlaceOrder(CustomerEntity customerEntity) {
        String beerToOrder = getRandomBeerUpc();

        BeerOrderLineDto beerOrderLine = BeerOrderLineDto.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(6)) //todo externalize value to property
                .build();

        List<BeerOrderLineDto> beerOrderLineSet = new ArrayList<>();
        beerOrderLineSet.add(beerOrderLine);

        BeerOrderDto beerOrder = BeerOrderDto.builder()
                .customerId(customerEntity.getId())
                .customerRef(UUID.randomUUID().toString())
                .beerOrderLines(beerOrderLineSet)
                .build();

        BeerOrderDto savedOrder = beerOrderService.placeOrder(customerEntity.getId(), beerOrder);

    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size()));
    }
}
