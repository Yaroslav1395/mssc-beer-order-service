package sakhno.sfg.beer.order.service.services.test_components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.config.JmsConfig;
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderRequest;
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderResult;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidationListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listenValidateOrderQueue(Message message) {
        boolean isValid = true;
        boolean sendResponse = true;
        log.info("Запрос на валидацию в очередь на тесте");
        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();
        if(request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals("fail-validation")) {
            isValid = false;
        } else if (request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals("dont-validate")) {
            sendResponse = false;
        }
        if(sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                    .isValid(isValid)
                    .orderId(request.getBeerOrderDto().getId())
                    .build());
        }
    }
}
