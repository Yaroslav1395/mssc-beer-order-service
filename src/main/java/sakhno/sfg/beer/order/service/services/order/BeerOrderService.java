package sakhno.sfg.beer.order.service.services.order;


import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {
    /**
     * Метод позволяет найти список заказов заказчика по id
     * @param customerId - id заказчика
     * @param pageable - параметры пагинации
     * @return - список заказов с пагинацией
     */
    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    /**
     * Метод позволяет создать новый заказ
     * @param customerId - id заказчика
     * @param beerOrderDto - заказ пива
     * @return - новый заказ пива
     */
    BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);

    /**
     * Метод позволяет получить заказ по его id и id заказчика
     * @param customerId - id заказчика
     * @param orderId - id заказа
     * @return - заказ
     */
    BeerOrderDto getOrderById(UUID customerId, UUID orderId);

    /**
     * Метод позволяет найти заказ по id и id заказчика с изменением его статуса на "Просмотрен".
     * @param customerId - id заказчика
     * @param orderId - id заказа
     */
    void pickupOrder(UUID customerId, UUID orderId);
}
