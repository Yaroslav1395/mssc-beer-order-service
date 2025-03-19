package sakhno.sfg.beer.order.service.services.test_components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.config.JmsConfig;
import sakhno.sfg.beer.order.service.web.model.events.AllocateOrderRequest;
import sakhno.sfg.beer.order.service.web.model.events.AllocateOrderResult;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderAllocationListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATION_ORDER_QUEUE)
    public void listenAllocateOrderQueue(Message message) {
        AllocateOrderRequest request = (AllocateOrderRequest) message.getPayload();
        boolean pendingInventory = false;
        boolean allocationError = false;
        boolean sendResponse = true;

        if(request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals("partial-allocation")) {
            pendingInventory = true;
        }

        if(request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals("fail-allocation")) {
            allocationError = true;
        } else if (request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals("dont-allocate")) {
            sendResponse = false;
        }
        boolean pendingInventoryFinal = pendingInventory;
        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if(pendingInventoryFinal) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            }else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });
        if(sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE,
                    AllocateOrderResult.builder()
                            .beerOrderDto(request.getBeerOrderDto())
                            .pendingInventory(pendingInventory)
                            .allocationError(allocationError)
                            .build());
        }
    }
}
