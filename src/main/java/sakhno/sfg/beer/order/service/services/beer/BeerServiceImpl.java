package sakhno.sfg.beer.order.service.services.beer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sakhno.sfg.beer.order.service.web.model.beer.BeerDto;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final String BEER_PATH = "/api/v1/beer/";
    private final String BEER_UPC_PATH = "/api/v2/beer/";
    @Value("${sfg.brewery.beer-service-host}")
    private String beerServiceHost;
    private final RestTemplate restTemplate;

    /**
     * Метод позволяет получить информацию о пиве по id. Запрос отправляет на сервис beer-service
     * @param beerId - id пива
     * @return - опциональная модель пива
     */
    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        return Optional.of(restTemplate.getForObject(
                beerServiceHost + BEER_PATH + beerId.toString(), BeerDto.class));
    }

    /**
     * Метод позволяет получить информацию о пиве по UPC. Запрос отправляет на сервис beer-service
     * @param upc - UPC пива
     * @return - опциональная модель пива
     */
    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(
                beerServiceHost + BEER_UPC_PATH + upc, BeerDto.class
        ));
    }
}
