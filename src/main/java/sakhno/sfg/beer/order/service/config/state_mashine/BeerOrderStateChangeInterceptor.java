package sakhno.sfg.beer.order.service.config.state_mashine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderEventEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.services.order.BeerOrderManagerServiceImpl;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;

    @Override
    @Transactional
    public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state, Message<BeerOrderEventEnum> message, Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition, StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.of(msg.getHeaders().get(BeerOrderManagerServiceImpl.ORDER_ID_HEADER)))
                .filter(value -> value instanceof UUID)  // проверяем, что значение действительно UUID
                .map(value -> (UUID) value)  // приводим к UUID
                .ifPresent(orderId -> {
                    log.info("Сохранение статуса заказа. Id заказа: {}. Статус: {}", orderId, state.getId());
                    BeerOrderEntity beerOrder = beerOrderRepository.findById(orderId).get();
                    beerOrder.setOrderStatus(state.getId());
                    beerOrderRepository.saveAndFlush(beerOrder);
                });
    }
}
