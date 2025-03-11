package sakhno.sfg.beer.order.service.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.services.beer.BeerService;
import sakhno.sfg.beer.order.service.web.model.BeerDto;
import sakhno.sfg.beer.order.service.web.model.BeerOrderLineDto;

import java.util.Optional;

@Component
public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {
    private BeerService beerService;
    private BeerOrderLineMapper delegate;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLineEntity beerOrderLineEntity) {
        BeerOrderLineDto orderLineDto = delegate.beerOrderLineToDto(beerOrderLineEntity);
        Optional<BeerDto> beer = beerService.getBeerById(beerOrderLineEntity.getBeerId());
        beer.ifPresent(beerDto -> {
            orderLineDto.setBeerName(beerDto.getBeerName());
            orderLineDto.setBeerStyle(beerDto.getBeerStyle());
            orderLineDto.setPrice(beerDto.getPrice());
            orderLineDto.setBeerId(beerDto.getId());
        });
        return orderLineDto;
    }
}
