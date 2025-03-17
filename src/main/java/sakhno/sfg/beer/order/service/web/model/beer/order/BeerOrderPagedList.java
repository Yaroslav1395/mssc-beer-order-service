package sakhno.sfg.beer.order.service.web.model.beer.order;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Класс необходим для поддержки пагинации
 */
public class BeerOrderPagedList extends PageImpl<BeerOrderDto> {

    public BeerOrderPagedList(List<BeerOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerOrderPagedList(List<BeerOrderDto> content) {
        super(content);
    }
}
