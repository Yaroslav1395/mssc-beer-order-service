package sakhno.sfg.beer.order.service.config.state_mashine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import sakhno.sfg.beer.order.service.domain.BeerOrderEventEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validateOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocationOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validationFailureAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocationFailureAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> deallocateOrderAction;

    @Autowired
    public BeerOrderStateMachineConfig(
            @Qualifier("validateOrderAction") Action<BeerOrderStatusEnum, BeerOrderEventEnum> validateOrderAction,
            @Qualifier("allocatedOrderAction") Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocationOrderAction,
            @Qualifier("validationFailureAction") Action<BeerOrderStatusEnum, BeerOrderEventEnum> validationFailureAction,
            @Qualifier("allocationFailureAction") Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocationFailureAction,
            @Qualifier("deallocateOrderAction") Action<BeerOrderStatusEnum, BeerOrderEventEnum> deallocateOrderAction) {
        this.validateOrderAction = validateOrderAction;
        this.allocationOrderAction = allocationOrderAction;
        this.validationFailureAction = validationFailureAction;
        this.allocationFailureAction = allocationFailureAction;
        this.deallocateOrderAction = deallocateOrderAction;
    }

    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(BeerOrderStatusEnum.NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(BeerOrderStatusEnum.PICKED_UP)
                .end(BeerOrderStatusEnum.DELIVERED)
                .end(BeerOrderStatusEnum.CANCELLED)
                .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION)
                .end(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
                .end(BeerOrderStatusEnum.ALLOCATION_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(BeerOrderStatusEnum.NEW)
                    .target(BeerOrderStatusEnum.VALIDATION_PENDING)
                    .event(BeerOrderEventEnum.VALIDATE_ORDER)
                    .action(validateOrderAction)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING)
                    .target(BeerOrderStatusEnum.VALIDATED)
                    .event(BeerOrderEventEnum.VALIDATION_PASSED)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING)
                    .target(BeerOrderStatusEnum.CANCELLED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING)
                    .target(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
                    .event(BeerOrderEventEnum.VALIDATION_FAILED)
                    .action(validationFailureAction)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.VALIDATED)
                    .target(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .event(BeerOrderEventEnum.ALLOCATE_ORDER)
                    .action(allocationOrderAction)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.VALIDATED)
                    .target(BeerOrderStatusEnum.CANCELLED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .target(BeerOrderStatusEnum.ALLOCATED)
                    .event(BeerOrderEventEnum.ALLOCATION_SUCCESS)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .target(BeerOrderStatusEnum.ALLOCATION_EXCEPTION)
                    .event(BeerOrderEventEnum.ALLOCATION_FAILED)
                    .action(allocationFailureAction)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .target(BeerOrderStatusEnum.CANCELLED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .target(BeerOrderStatusEnum.PENDING_INVENTORY)
                    .event(BeerOrderEventEnum.ALLOCATION_NO_INVENTORY)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATED)
                    .target(BeerOrderStatusEnum.PICKED_UP)
                    .event(BeerOrderEventEnum.BEER_ORDER_PICKED_UP)
                .and()
                .withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATED)
                    .target(BeerOrderStatusEnum.CANCELLED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                    .action(deallocateOrderAction);
    }
}
