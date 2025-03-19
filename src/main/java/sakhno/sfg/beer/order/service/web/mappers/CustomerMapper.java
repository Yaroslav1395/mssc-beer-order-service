package sakhno.sfg.beer.order.service.web.mappers;

import org.mapstruct.Mapper;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.web.model.customer.CustomerDto;

@Mapper(uses = DateMapper.class)
public interface CustomerMapper {

    CustomerDto customerToDto(CustomerEntity entity);

    CustomerEntity dtoToCustomer(CustomerDto dto);
}
