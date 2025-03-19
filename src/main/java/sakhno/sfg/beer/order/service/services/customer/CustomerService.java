package sakhno.sfg.beer.order.service.services.customer;


import org.springframework.data.domain.Pageable;
import sakhno.sfg.beer.order.service.web.model.customer.CustomerPageList;

public interface CustomerService {

    CustomerPageList listCustomers(Pageable pageable);
}
