package sakhno.sfg.beer.order.service.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.config.JmsConfig;
import sakhno.sfg.beer.order.service.services.order.BeerOrderManagerService;
import sakhno.sfg.beer.order.service.web.model.events.AllocateOrderResult;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderAllocationResultListener {
    private final BeerOrderManagerService beerOrderManagerService;

    /**
     * Метод прослушивает очередь в которую попадают результаты распределения заказа на складе.
     * @param allocateOrderResult - результат распределения
     */
    @JmsListener(destination = JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE)
    public void listenerAllocationResult(AllocateOrderResult allocateOrderResult) {
        if(!allocateOrderResult.getAllocationError() && !allocateOrderResult.getPendingInventory()) {
            beerOrderManagerService.beerOrderAllocationPassed(allocateOrderResult.getBeerOrderDto());
        } else if (!allocateOrderResult.getAllocationError()) {
            beerOrderManagerService.beerOrderAllocationPendingInventory(allocateOrderResult.getBeerOrderDto());
        } else if (!allocateOrderResult.getPendingInventory()) {
            beerOrderManagerService.beerOrderAllocationFailed(allocateOrderResult.getBeerOrderDto());
        }
    }
}
