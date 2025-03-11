package sakhno.sfg.beer.order.service.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "beer_order_lines")
public class BeerOrderLineEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_order_id", columnDefinition = "varchar", nullable = false)
    private BeerOrderEntity beerOrder;

    @Column(name = "beer_id", columnDefinition = "varchar")
    private UUID beerId;

    private String upc;

    @Column(name = "order_quantity")
    private Integer orderQuantity = 0;

    @Column(name = "quantity_allocated")
    private Integer quantityAllocated = 0;

    @Builder
    public BeerOrderLineEntity(UUID id, String upc, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                               BeerOrderEntity beerOrder, UUID beerId, Integer orderQuantity,
                               Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerOrder = beerOrder;
        this.beerId = beerId;
        this.upc = upc;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }
}
