package sakhno.sfg.beer.order.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

@Repository
public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLineEntity, UUID> {
}
