package sakhno.sfg.beer.order.service.config.state_mashine.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.config.JmsConfig;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderEventEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.services.order.BeerOrderManagerServiceImpl;
import sakhno.sfg.beer.order.service.web.mappers.BeerOrderMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllocatedOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    /**
     * Метод позволяет отправить сообщение в очередь на размещение заказа. Заказ размещается на складе.
     * @param stateContext - контекст машины состояний
     */
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerServiceImpl.ORDER_ID_HEADER);
        BeerOrderEntity beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_ORDER_QUEUE,
                beerOrderMapper.beerOrderToDto(beerOrder));
        log.info("Отправка на распределение в очередь заказа с id: {}", beerOrderId);

    }
}
