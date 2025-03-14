package sakhno.sfg.beer.order.service.web.mappers;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.web.model.BeerOrderLineDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T16:31:53+0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
@Primary
public class BeerOrderLineMapperImpl extends BeerOrderLineMapperDecorator {

    @Autowired
    @Qualifier("delegate")
    private BeerOrderLineMapper delegate;

    @Override
    public BeerOrderLineEntity dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto)  {
        return delegate.dtoToBeerOrderLine( beerOrderLineDto );
    }
}
