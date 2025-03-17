package sakhno.sfg.beer.order.service.services.order;

import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;

import java.util.UUID;

public interface BeerOrderManagerService {
    /**
     * Метод позволяет создать новый заказ. Под заказ создается машина состояний
     * @param beerOrderEntity - сущность заказа
     * @return - сохраненный заказ
     */
    BeerOrderEntity newBeerOrder(BeerOrderEntity beerOrderEntity);

    /**
     * Метод обрабатывает результат валидации заказа. Исходя из результата производится переход машины состояния,
     * через отправку события
     * @param beerOrderId - id заказа
     * @param isValid - результат валидации
     */
    void processValidationResult(UUID beerOrderId, Boolean isValid);

    /**
     * Метод обрабатывает результат размещения заказа, в случае если размещение прошло успешно.
     * Обрабатывается переход машины состояний через отправку соответствующего события
     * @param beerOrderDto - заказ пива
     */
    void beerOrderAllocationPassed(BeerOrderDto beerOrderDto);

    /**
     * Метод обрабатывает результат размещения заказа в случае если количества пива на складе не достаточно,
     * чтобы обработать заказ.
     * @param beerOrderDto - заказ пива
     */
    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);

    /**
     * Метод обрабатывает результат размещения заказа в случае если произошла ошибка.
     * Осуществляется переход машины состояний через отправку соответствующего события
     * @param beerOrderDto - заказ пива
     */
    void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);

}
