package sakhno.sfg.beer.order.service.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sakhno.sfg.beer.order.service.config.state_mashine.BeerOrderStateChangeInterceptor;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderEventEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerOrderManagerServiceImpl implements BeerOrderManagerService {
    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";
    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor;

    /**
     * Метод позволяет создать новый заказ. Под заказ создается машина состояний
     * @param beerOrderEntity - сущность заказа
     * @return - сохраненный заказ
     */
    @Override
    @Transactional
    public BeerOrderEntity newBeerOrder(BeerOrderEntity beerOrderEntity) {
        log.info("Создание нового заказа. Id: {}", beerOrderEntity.getId());
        beerOrderEntity.setId(null);
        beerOrderEntity.setOrderStatus(BeerOrderStatusEnum.NEW);
        BeerOrderEntity savedBeerOrder = beerOrderRepository.save(beerOrderEntity);
        log.info("Заказ создан: {}", savedBeerOrder);
        sendBeerOrderEvent(savedBeerOrder, BeerOrderEventEnum.VALIDATE_ORDER);
        return savedBeerOrder;
    }

    /**
     * Метод обрабатывает результат валидации заказа. Исходя из результата производится переход машины состояния,
     * через отправку события
     * @param beerOrderId - id заказа
     * @param isValid - результат валидации
     */
    @Override
    @Transactional
    public void processValidationResult(UUID beerOrderId, Boolean isValid) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(beerOrderId);

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            if(isValid) {
                sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_PASSED);
                BeerOrderEntity validatedOrder = beerOrderRepository.findById(beerOrderId).get();
                sendBeerOrderEvent(validatedOrder, BeerOrderEventEnum.ALLOCATE_ORDER);
            } else {
                sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_FAILED);
            }
        }, logOrderNotFound(beerOrderId));
    }

    /**
     * Метод обрабатывает результат размещения заказа, в случае если размещение прошло успешно.
     * Осуществляется переход машины состояний через отправку соответствующего события
     * @param beerOrderDto - заказ пива
     */
    @Override
    public void beerOrderAllocationPassed(BeerOrderDto beerOrderDto) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        beerOrderOptional.ifPresentOrElse(beerOrderEntity -> {
            sendBeerOrderEvent(beerOrderEntity, BeerOrderEventEnum.ALLOCATION_SUCCESS);
            updateAllocatedQty(beerOrderDto);
        }, logOrderNotFound(beerOrderDto.getId()));
    }

    /**
     * Метод обрабатывает результат размещения заказа в случае если количества пива на складе не достаточно,
     * чтобы обработать заказ. Осуществляется переход машины состояний через отправку соответствующего события
     * @param beerOrderDto - заказ пива
     */
    @Override
    public void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());
        beerOrderOptional.ifPresentOrElse(beerOrderEntity -> {
            sendBeerOrderEvent(beerOrderEntity, BeerOrderEventEnum.ALLOCATION_NO_INVENTORY);
            updateAllocatedQty(beerOrderDto);
        }, logOrderNotFound(beerOrderDto.getId()));
    }

    /**
     * Метод обрабатывает результат размещения заказа в случае если произошла ошибка.
     * Осуществляется переход машины состояний через отправку соответствующего события
     * @param beerOrderDto - заказ пива
     */
    @Override
    public void beerOrderAllocationFailed(BeerOrderDto beerOrderDto) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());
        beerOrderOptional.ifPresentOrElse(beerOrderEntity ->
                sendBeerOrderEvent(beerOrderEntity, BeerOrderEventEnum.ALLOCATION_FAILED),
                logOrderNotFound(beerOrderDto.getId()));
    }

    @Override
    public void beerOrderPikeUp(UUID id) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(id);
        beerOrderOptional.ifPresentOrElse(beerOrderEntity ->
                sendBeerOrderEvent(beerOrderEntity, BeerOrderEventEnum.BEER_ORDER_PICKED_UP), logOrderNotFound(id));
    }

    @Override
    public void cancelOrder(UUID id) {
        Optional<BeerOrderEntity> beerOrderOptional = beerOrderRepository.findById(id);
        beerOrderOptional.ifPresentOrElse(beerOrderEntity ->
                sendBeerOrderEvent(beerOrderEntity, BeerOrderEventEnum.CANCEL_ORDER), logOrderNotFound(id));
    }

    /**
     * Метод позволяет отправить событие в автомат статусов
     * @param beerOrderEntity - сущностьзаказа
     * @param event - событие
     */
    private void sendBeerOrderEvent(BeerOrderEntity beerOrderEntity, BeerOrderEventEnum event) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = build(beerOrderEntity);
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(ORDER_ID_HEADER, beerOrderEntity.getId())
                .build();
        sm.sendEvent(msg);
    }


    /**
     * Метод позволяет получить конечный автомат из фабрики автоматов. Останавливает его для избежания конфликтов.
     * Устанавливает инспектор автомата, который позволяет совершать действия при изменении состояния. Автомату
     * устанавливается текущее состояние заказа. Автомат запускается.
     * @param beerOrder - сущность заказа
     * @return - конечный автомат стстояний
     */
    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrderEntity beerOrder) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = stateMachineFactory.getStateMachine(beerOrder.getId());
        sm.stop();
        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(beerOrderStateChangeInterceptor);
            sma.resetStateMachine(new DefaultStateMachineContext<>(
                    beerOrder.getOrderStatus(), null, null, null));
        });
        sm.start();
        return sm;
    }

    /**
     * Метод позволяет установить количество размещенного заказа
     * @param beerOrderDto - заказ на пиво после размещения
     */
    private void updateAllocatedQty(BeerOrderDto beerOrderDto) {
        Optional<BeerOrderEntity> allocatedOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        allocatedOrderOptional.ifPresentOrElse(allocatedOrder ->
                allocatedOrder.getBeerOrderLines().forEach(beerOrderLineEntity -> {
                    beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
                        if(beerOrderLineEntity.getId().equals(beerOrderLineDto.getId())) {
                            beerOrderLineEntity.setQuantityAllocated(beerOrderLineDto.getQuantityAllocated());
                        }});
                    beerOrderRepository.saveAndFlush(allocatedOrder);
                }), logOrderNotFound(beerOrderDto.getId()));
    }

    private Runnable logOrderNotFound(UUID id) {
        return () -> log.error("Не найдено заказа в базе по id: {}", id);
    }
}
