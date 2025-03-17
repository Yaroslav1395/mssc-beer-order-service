package sakhno.sfg.beer.order.service.web.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;

/**
 * Объект для отправки в очередь при валидации заказа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderRequest {
    private BeerOrderDto beerOrderDto;
}
