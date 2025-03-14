package sakhno.sfg.beer.order.service.services.beer;

import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;

public interface BeerOrderManagerService {

    BeerOrderEntity newBeerOrder(BeerOrderEntity beerOrderEntity);
}
