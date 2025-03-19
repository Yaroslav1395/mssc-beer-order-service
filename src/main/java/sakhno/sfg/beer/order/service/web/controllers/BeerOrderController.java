package sakhno.sfg.beer.order.service.web.controllers;

import lombok.RequiredArgsConstructor;
import sakhno.sfg.beer.order.service.services.order.BeerOrderService;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderDto;
import sakhno.sfg.beer.order.service.web.model.beer.order.BeerOrderPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customer/{customerId}/")
@RestController
@RequiredArgsConstructor
public class BeerOrderController {
    private final BeerOrderService beerOrderService;

    @GetMapping("orders")
    public BeerOrderPagedList listOrders(@PathVariable("customerId") UUID customerId,
                                         @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize){
        return beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(@PathVariable("customerId") UUID customerId, @RequestBody BeerOrderDto beerOrderDto){
        return beerOrderService.placeOrder(customerId, beerOrderDto);
    }

    @GetMapping("orders/{orderId}")
    public BeerOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId){
        return beerOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId){
        beerOrderService.pickupOrder(customerId, orderId);
    }
}
