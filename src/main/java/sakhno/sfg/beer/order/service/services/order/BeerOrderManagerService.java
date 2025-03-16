package sakhno.sfg.beer.order.service.services.order;

import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.web.model.BeerOrderDto;

import java.util.UUID;

public interface BeerOrderManagerService {

    BeerOrderEntity newBeerOrder(BeerOrderEntity beerOrderEntity);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDto beerOrderDto);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);

    void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);

}
