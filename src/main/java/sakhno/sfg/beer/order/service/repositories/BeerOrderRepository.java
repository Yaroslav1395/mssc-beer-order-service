package sakhno.sfg.beer.order.service.repositories;


import org.springframework.stereotype.Repository;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

@Repository
public interface BeerOrderRepository extends JpaRepository<BeerOrderEntity, UUID> {

    /**
     * Метод позволяет найти все заказы по сущности заказчика
     * @param customerEntity - сущность заказчика
     * @param pageable - параметры пагинации
     * @return - список заказов с пагинацией
     */
    Page<BeerOrderEntity> findAllByCustomer(CustomerEntity customerEntity, Pageable pageable);

    /**
     * Метод позволяет найти все заказы по статусу
     * @param beerOrderStatusEnum - статус заказа
     * @return - список заказов
     */
    List<BeerOrderEntity> findAllByOrderStatus(BeerOrderStatusEnum beerOrderStatusEnum);
}
