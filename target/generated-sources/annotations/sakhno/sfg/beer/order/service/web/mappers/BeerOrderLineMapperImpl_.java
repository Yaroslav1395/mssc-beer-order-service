package sakhno.sfg.beer.order.service.web.mappers;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.web.model.BeerOrderLineDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T21:27:37+0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerOrderLineMapperImpl_ implements BeerOrderLineMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLineEntity beerOrderLineEntity) {
        if ( beerOrderLineEntity == null ) {
            return null;
        }

        BeerOrderLineDto.BeerOrderLineDtoBuilder beerOrderLineDto = BeerOrderLineDto.builder();

        beerOrderLineDto.id( beerOrderLineEntity.getId() );
        if ( beerOrderLineEntity.getVersion() != null ) {
            beerOrderLineDto.version( beerOrderLineEntity.getVersion().intValue() );
        }
        beerOrderLineDto.createdDate( dateMapper.asOffsetDateTime( beerOrderLineEntity.getCreatedDate() ) );
        beerOrderLineDto.lastModifiedDate( dateMapper.asOffsetDateTime( beerOrderLineEntity.getLastModifiedDate() ) );
        beerOrderLineDto.upc( beerOrderLineEntity.getUpc() );
        beerOrderLineDto.beerId( beerOrderLineEntity.getBeerId() );
        beerOrderLineDto.orderQuantity( beerOrderLineEntity.getOrderQuantity() );

        return beerOrderLineDto.build();
    }

    @Override
    public BeerOrderLineEntity dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
        if ( beerOrderLineDto == null ) {
            return null;
        }

        BeerOrderLineEntity.BeerOrderLineEntityBuilder beerOrderLineEntity = BeerOrderLineEntity.builder();

        beerOrderLineEntity.id( beerOrderLineDto.getId() );
        beerOrderLineEntity.upc( beerOrderLineDto.getUpc() );
        if ( beerOrderLineDto.getVersion() != null ) {
            beerOrderLineEntity.version( beerOrderLineDto.getVersion().longValue() );
        }
        beerOrderLineEntity.createdDate( dateMapper.asTimestamp( beerOrderLineDto.getCreatedDate() ) );
        beerOrderLineEntity.lastModifiedDate( dateMapper.asTimestamp( beerOrderLineDto.getLastModifiedDate() ) );
        beerOrderLineEntity.beerId( beerOrderLineDto.getBeerId() );
        beerOrderLineEntity.orderQuantity( beerOrderLineDto.getOrderQuantity() );

        return beerOrderLineEntity.build();
    }
}
