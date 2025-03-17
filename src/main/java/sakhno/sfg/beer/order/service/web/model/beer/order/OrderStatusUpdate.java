package sakhno.sfg.beer.order.service.web.model.beer.order;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sakhno.sfg.beer.order.service.web.model.beer.BaseItemDto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Класс необходим для редактирования заказа на пиво
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderStatusUpdate extends BaseItemDto {
    private UUID orderId;
    private String customerRef;
    private String orderStatus;

    @Builder
    public OrderStatusUpdate(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                             UUID orderId, String orderStatus, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customerRef = customerRef;
    }
}
