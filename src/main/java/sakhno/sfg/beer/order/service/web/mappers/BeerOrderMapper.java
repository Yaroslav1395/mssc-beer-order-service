package sakhno.sfg.beer.order.service.web.mappers;

import org.mapstruct.Mapping;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;
import org.mapstruct.Mapper;



@Mapper(uses = {DateMapper.class})
public interface BeerOrderMapper {

    /**
     * Метод позволяет преобразовать сущность заказа пива в DTO
     * @param beerOrderEntity - сущность заказа пива
     * @return - DTO заказа пива
     */
    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrderEntity beerOrderEntity);

    /**
     * Метод позволяет преобразовать DTO заказа пива в сущность
     * @param dto - DTO заказа пива
     * @return - сущность заказа пива
     */
    BeerOrderEntity dtoToBeerOrder(BeerOrderDto dto);
}
