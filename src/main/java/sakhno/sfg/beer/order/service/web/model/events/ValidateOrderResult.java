package sakhno.sfg.beer.order.service.web.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Объект для получения из очереди результата валидации заказа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderResult {
    private UUID orderId;
    private Boolean isValid;
}
