package sakhno.sfg.beer.order.service.web.mappers;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.web.model.customer.CustomerDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T15:29:43+0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public CustomerDto customerToDto(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CustomerDto.CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.id( entity.getId() );
        if ( entity.getVersion() != null ) {
            customerDto.version( entity.getVersion().intValue() );
        }
        customerDto.createdDate( dateMapper.asOffsetDateTime( entity.getCreatedDate() ) );
        customerDto.lastModifiedDate( dateMapper.asOffsetDateTime( entity.getLastModifiedDate() ) );
        customerDto.customerName( entity.getCustomerName() );

        return customerDto.build();
    }

    @Override
    public CustomerEntity dtoToCustomer(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        CustomerEntity.CustomerEntityBuilder customerEntity = CustomerEntity.builder();

        customerEntity.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            customerEntity.version( dto.getVersion().longValue() );
        }
        customerEntity.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        customerEntity.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        customerEntity.customerName( dto.getCustomerName() );

        return customerEntity.build();
    }
}
