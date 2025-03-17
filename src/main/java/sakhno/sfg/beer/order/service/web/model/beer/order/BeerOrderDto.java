package sakhno.sfg.beer.order.service.web.model.beer.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.web.model.beer.BaseItemDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Класс описывает свойства заказа на пиво
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDto extends BaseItemDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID customerId;
    private String customerRef;
    private List<BeerOrderLineDto> beerOrderLines;
    private BeerOrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;

    @Builder
    public BeerOrderDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID customerId, List<BeerOrderLineDto> beerOrderLines,
                        BeerOrderStatusEnum orderStatus, String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }
}
