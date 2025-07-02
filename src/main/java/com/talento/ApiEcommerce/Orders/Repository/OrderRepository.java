package com.talento.ApiEcommerce.Orders.Repository;

import com.talento.ApiEcommerce.Orders.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(Long customerId);
}
