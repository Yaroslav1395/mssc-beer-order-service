package sakhno.sfg.beer.order.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderLineEntity;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import sakhno.sfg.beer.order.service.repositories.BeerOrderRepository;
import sakhno.sfg.beer.order.service.repositories.CustomerRepository;
import sakhno.sfg.beer.order.service.services.beer.BeerServiceImpl;
import sakhno.sfg.beer.order.service.services.order.BeerOrderManagerService;
import sakhno.sfg.beer.order.service.web.model.beer.BeerDto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WireMockTest
@SpringBootTest(properties = "spring.liquibase.enabled=false")
public class BeerOrderManagerImplIT {
    @Autowired
    BeerOrderManagerService beerOrderManagerService;
    @Autowired
    BeerOrderRepository beerOrderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WireMockServer wireMockServer;
    CustomerEntity testCustomer;
    UUID beerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.save(CustomerEntity.builder()
                .customerName("testCustomer").build());
    }

    @TestConfiguration
    static class WireMockConfig {
        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            WireMockServer server = new WireMockServer(8083);
            server.start();
            return server;
        }
    }

    @Test
    void testNewToAllocated() throws JsonProcessingException, InterruptedException {
        BeerDto beerDto = BeerDto.builder().id(beerId).upc("12345").build();
        wireMockServer.stubFor(WireMock.get(BeerServiceImpl.BEER_UPC_PATH + "12345")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));
        BeerOrderEntity beerOrder = createBeerOrder();

        BeerOrderEntity savedBeerOrder = beerOrderManagerService.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            System.out.println(beerOrder.getId());
            BeerOrderEntity foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();
            assertEquals(BeerOrderStatusEnum.ALLOCATED, foundOrder.getOrderStatus());
        });

        BeerOrderEntity savedBeerOrder2 = beerOrderRepository.findById(savedBeerOrder.getId()).get();

        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder2.getOrderStatus());
    }

    public BeerOrderEntity createBeerOrder() {
        BeerOrderEntity beerOrder = BeerOrderEntity.builder()
                .customerEntity(testCustomer)
                .build();

        Set<BeerOrderLineEntity> lines = new HashSet<>();
        lines.add(BeerOrderLineEntity.builder()
                .beerId(beerId)
                .upc("12345")
                .orderQuantity(1)
                .beerOrder(beerOrder)
                .build());
        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }
}
