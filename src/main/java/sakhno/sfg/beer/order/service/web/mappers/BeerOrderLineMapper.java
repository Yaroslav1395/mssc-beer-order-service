package sakhno.sfg.beer.order.service.web.mappers;

import org.mapstruct.DecoratedWith;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {


    /**
     * Метод преобразует сущность пива находящегося в заказе в DTO
     * @param beerOrderLineEntity - сущность пива в заказе
     * @return - DTO пива в заказе
     */
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLineEntity beerOrderLineEntity);

    /**
     * Метод преобразует DTO пива находящегося в заказе в сущность
     * @param beerOrderLineDto - DTO пива в заказе
     * @return - сущность пива в заказе
     */
    BeerOrderLineEntity dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto);
}
