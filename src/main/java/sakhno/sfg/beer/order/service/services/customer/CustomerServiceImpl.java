package sakhno.sfg.beer.order.service.services.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.repositories.CustomerRepository;
import sakhno.sfg.beer.order.service.web.mappers.CustomerMapper;
import sakhno.sfg.beer.order.service.web.model.customer.CustomerPageList;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public CustomerPageList listCustomers(Pageable pageable) {
        Page<CustomerEntity> customerEntityPage = customerRepository.findAll(pageable);
        return new CustomerPageList(
                customerEntityPage.stream()
                        .map(customerMapper::customerToDto)
                        .collect(Collectors.toList()),
                PageRequest.of(customerEntityPage.getPageable().getPageNumber(),
                        customerEntityPage.getPageable().getPageSize()),
                customerEntityPage.getTotalElements());
    }
}
