package sakhno.sfg.beer.order.service.web.model.beer.order;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sakhno.sfg.beer.order.service.web.model.beer.BaseItemDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Класс описывает свойства пива находящегося в заказе
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDto extends BaseItemDto {
    private String upc;
    private String beerName;
    private String beerStyle;
    private UUID beerId;
    private Integer orderQuantity = 0;
    private BigDecimal price;
    private Integer quantityAllocated;

    @Builder
    public BeerOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            String upc, String beerName, String beerStyle, UUID beerId, Integer orderQuantity,
                            BigDecimal price, Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.upc = upc;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.beerId = beerId;
        this.orderQuantity = orderQuantity;
        this.price = price;
        this.quantityAllocated = quantityAllocated;
    }
}
