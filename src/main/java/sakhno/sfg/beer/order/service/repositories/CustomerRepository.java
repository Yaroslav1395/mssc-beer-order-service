package sakhno.sfg.beer.order.service.repositories;

import sakhno.sfg.beer.order.service.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    /**
     * Метод позволят найти заказчика по имени
     * @param customerName - имя заказчика
     * @return - список заказчиков
     */
    List<CustomerEntity> findAllByCustomerNameLike(String customerName);
}
