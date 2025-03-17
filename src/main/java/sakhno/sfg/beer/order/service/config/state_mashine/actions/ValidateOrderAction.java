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
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderRequest;

import java.util.UUID;

@Component("validateOrderAction")
@Slf4j
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    /**
     * Метод позволяет отправить в очередь сообщение на валидацию заказа. Проверяется список пива в заказе. Если в списке
     * есть несуществующее пиво, то валидацию заказ не пройдет
     * @param stateContext - контекст машины состояний
     */
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = stateContext.getMessage().getHeaders().get(BeerOrderManagerServiceImpl.ORDER_ID_HEADER).toString();
        BeerOrderEntity beerOrder = beerOrderRepository.findById(UUID.fromString(beerOrderId)).get();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                .build());
        log.info("Отправка запроса на валидацию заказа в очередь. Id заказа: {}", beerOrderId);
    }
}
