package sakhno.sfg.beer.order.service.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sakhno.sfg.beer.order.service.services.customer.CustomerService;
import sakhno.sfg.beer.order.service.web.model.customer.CustomerPageList;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public CustomerPageList listCustomers(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "25", required = false) Integer pageSize) {
        return customerService.listCustomers(PageRequest.of(pageNumber, pageSize));
    }
}
