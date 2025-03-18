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
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        AllocateOrderRequest request = (AllocateOrderRequest) message.getPayload();
        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto ->{
            System.out.println(beerOrderLineDto.getOrderQuantity());
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            });
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                    .beerOrderDto(request.getBeerOrderDto())
                    .pendingInventory(false)
                    .allocationError(false)
                    .build());
    }
}
