package sakhno.sfg.beer.order.service.services.beer;

import sakhno.sfg.beer.order.service.web.model.beer.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    /**
     * Метод позволяет получить информацию о пиве по id. Запрос отправляет на сервис beer-service
     * @param beerId - id пива
     * @return - опциональная модель пива
     */
    Optional<BeerDto> getBeerById(UUID beerId);

    /**
     * Метод позволяет получить информацию о пиве по UPC. Запрос отправляет на сервис beer-service
     * @param upc - UPC пива
     * @return - опциональная модель пива
     */
    Optional<BeerDto> getBeerByUpc(String upc);
}
