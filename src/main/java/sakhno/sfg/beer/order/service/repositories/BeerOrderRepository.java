package sakhno.sfg.beer.order.service.repositories;


import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;
import sakhno.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import sakhno.sfg.beer.order.service.domain.BeerOrderEntity;
import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;


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

    /**
     * Метод позволяет найти заказ по его UUID. @Lock - аннотация говорит Hibernate, что при выполнении
     * метода нужно заблокировать строку в таблице для записи.
     * @param id - uuid заказа
     * @return - заказ
     */
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //BeerOrderEntity findOneById(UUID id);
}
