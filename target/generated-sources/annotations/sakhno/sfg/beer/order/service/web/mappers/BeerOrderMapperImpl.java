package sakhno.sfg.beer.order.service.web.mappers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.web.model.BeerOrderDto;
import sakhno.sfg.beer.order.service.web.model.BeerOrderLineDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T20:14:50+0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class BeerOrderMapperImpl implements BeerOrderMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerOrderDto beerOrderToDto(BeerOrderEntity beerOrderEntity) {
        if ( beerOrderEntity == null ) {
            return null;
        }

        BeerOrderDto.BeerOrderDtoBuilder beerOrderDto = BeerOrderDto.builder();

        beerOrderDto.customerId( beerOrderEntityCustomerId( beerOrderEntity ) );
        beerOrderDto.id( beerOrderEntity.getId() );
        if ( beerOrderEntity.getVersion() != null ) {
            beerOrderDto.version( beerOrderEntity.getVersion().intValue() );
        }
        beerOrderDto.createdDate( dateMapper.asOffsetDateTime( beerOrderEntity.getCreatedDate() ) );
        beerOrderDto.lastModifiedDate( dateMapper.asOffsetDateTime( beerOrderEntity.getLastModifiedDate() ) );
        beerOrderDto.beerOrderLines( beerOrderLineEntitySetToBeerOrderLineDtoList( beerOrderEntity.getBeerOrderLines() ) );
        beerOrderDto.orderStatus( beerOrderEntity.getOrderStatus() );
        beerOrderDto.orderStatusCallbackUrl( beerOrderEntity.getOrderStatusCallbackUrl() );
        beerOrderDto.customerRef( beerOrderEntity.getCustomerRef() );

        return beerOrderDto.build();
    }

    @Override
    public BeerOrderEntity dtoToBeerOrder(BeerOrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrderEntity.BeerOrderEntityBuilder beerOrderEntity = BeerOrderEntity.builder();

        beerOrderEntity.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beerOrderEntity.version( dto.getVersion().longValue() );
        }
        beerOrderEntity.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beerOrderEntity.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        beerOrderEntity.customerRef( dto.getCustomerRef() );
        beerOrderEntity.beerOrderLines( beerOrderLineDtoListToBeerOrderLineEntitySet( dto.getBeerOrderLines() ) );
        beerOrderEntity.orderStatus( dto.getOrderStatus() );
        beerOrderEntity.orderStatusCallbackUrl( dto.getOrderStatusCallbackUrl() );

        return beerOrderEntity.build();
    }

    private UUID beerOrderEntityCustomerId(BeerOrderEntity beerOrderEntity) {
        if ( beerOrderEntity == null ) {
            return null;
        }
        CustomerEntity customer = beerOrderEntity.getCustomer();
        if ( customer == null ) {
            return null;
        }
        UUID id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected BeerOrderLineDto beerOrderLineEntityToBeerOrderLineDto(BeerOrderLineEntity beerOrderLineEntity) {
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

    protected List<BeerOrderLineDto> beerOrderLineEntitySetToBeerOrderLineDtoList(Set<BeerOrderLineEntity> set) {
        if ( set == null ) {
            return null;
        }

        List<BeerOrderLineDto> list = new ArrayList<BeerOrderLineDto>( set.size() );
        for ( BeerOrderLineEntity beerOrderLineEntity : set ) {
            list.add( beerOrderLineEntityToBeerOrderLineDto( beerOrderLineEntity ) );
        }

        return list;
    }

    protected BeerOrderLineEntity beerOrderLineDtoToBeerOrderLineEntity(BeerOrderLineDto beerOrderLineDto) {
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

    protected Set<BeerOrderLineEntity> beerOrderLineDtoListToBeerOrderLineEntitySet(List<BeerOrderLineDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<BeerOrderLineEntity> set = new LinkedHashSet<BeerOrderLineEntity>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( BeerOrderLineDto beerOrderLineDto : list ) {
            set.add( beerOrderLineDtoToBeerOrderLineEntity( beerOrderLineDto ) );
        }

        return set;
    }
}
