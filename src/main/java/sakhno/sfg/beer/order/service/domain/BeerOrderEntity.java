package sakhno.sfg.beer.order.service.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "beer_orders")
public class BeerOrderEntity extends BaseEntity {
    @Column(name = "customer_ref")
    private String customerRef;

    @ManyToOne
    @JoinColumn(name = "customer_id", columnDefinition = "varchar", nullable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<BeerOrderLineEntity> beerOrderLines;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private BeerOrderStatusEnum orderStatus = BeerOrderStatusEnum.NEW;

    @Column(name = "order_status_callback_url")
    private String orderStatusCallbackUrl;

    @Builder
    public BeerOrderEntity(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String customerRef, CustomerEntity customerEntity,
                           Set<BeerOrderLineEntity> beerOrderLines, BeerOrderStatusEnum orderStatus,
                           String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customerEntity;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }

    @Override
    public String toString() {
        return "BeerOrderEntity{" +
                "customerRef='" + customerRef + '\'' +
                ", customer=" + customer +
                ", orderStatus=" + orderStatus +
                ", orderStatusCallbackUrl='" + orderStatusCallbackUrl + '\'' +
                '}';
    }
}
