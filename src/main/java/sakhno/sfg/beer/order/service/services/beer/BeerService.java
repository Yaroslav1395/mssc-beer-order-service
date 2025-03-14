package sakhno.sfg.beer.order.service.services.beer;

import sakhno.sfg.beer.order.service.web.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID beerId);

    Optional<BeerDto> getBeerByUpc(String upc);
}
