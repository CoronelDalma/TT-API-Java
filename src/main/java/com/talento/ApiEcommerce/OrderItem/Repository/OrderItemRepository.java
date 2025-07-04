package com.talento.ApiEcommerce.OrderItem.Repository;

import com.talento.ApiEcommerce.OrderItem.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderIdAndArticuloId(Long orderId, Long articuloId);
}
