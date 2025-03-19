package sakhno.sfg.beer.order.service.web.model.customer;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sakhno.sfg.beer.order.service.web.model.beer.BaseItemDto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Класс описывает свойства покупателя
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends BaseItemDto {
    private String customerName;

    @Builder
    public CustomerDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String name) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerName = name;
    }
}
