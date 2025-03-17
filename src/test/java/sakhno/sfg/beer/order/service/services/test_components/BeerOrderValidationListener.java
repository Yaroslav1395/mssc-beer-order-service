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
        log.info("Запрос на валидацию в очередь на тесте");
        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                .isValid(Boolean.TRUE)
                .orderId(request.getBeerOrderDto().getId())
                .build());
    }
}
