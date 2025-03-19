package sakhno.sfg.beer.order.service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "api_key",  length = 36, columnDefinition = "varchar")
    private UUID apiKey;

    @OneToMany(mappedBy = "customer")
    private Set<BeerOrderEntity> beerOrders;

    @Builder
    public CustomerEntity(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String customerName,
                          UUID apiKey, Set<BeerOrderEntity> beerOrdersEntity) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerName = customerName;
        this.apiKey = apiKey;
        this.beerOrders = beerOrdersEntity;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "customerName='" + customerName + '\'' +
                ", apiKey=" + apiKey +
                '}';
    }
}
