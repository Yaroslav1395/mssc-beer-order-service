package sakhno.sfg.beer.order.service.repositories;

import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLineEntity, UUID> {
}
