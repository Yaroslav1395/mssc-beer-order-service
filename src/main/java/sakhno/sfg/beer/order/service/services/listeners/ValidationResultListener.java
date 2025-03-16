package sakhno.sfg.beer.order.service.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.config.JmsConfig;
import sakhno.sfg.beer.order.service.services.order.BeerOrderManagerService;
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderResult;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidationResultListener {
    private final BeerOrderManagerService beerOrderManagerService;
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listenValidateOrderResult(ValidateOrderResult validateOrderResult) {
        final UUID beerOrderId = validateOrderResult.getOrderId();
        log.info("Результат валидации заказа с id: {}", beerOrderId);
        beerOrderManagerService.processValidationResult(beerOrderId, validateOrderResult.getIsValid());
    }
}
