package sakhno.sfg.beer.order.service.services.beer;

import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;

import java.util.UUID;

public interface BeerOrderManagerService {

    BeerOrderEntity newBeerOrder(BeerOrderEntity beerOrderEntity);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

}
