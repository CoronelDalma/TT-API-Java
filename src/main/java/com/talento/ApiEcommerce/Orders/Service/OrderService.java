package com.talento.ApiEcommerce.Orders.Service;

import com.talento.ApiEcommerce.OrderItem.Model.OrderItem;
import com.talento.ApiEcommerce.Orders.Model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAll();
    Optional<Order> getOrderById(Long id);
    Order save(Order order);
    void delete(Long id);
    Optional<OrderItem> updateQty(Long orderId, Long itemId, int qty);
    Optional<OrderItem> addItemToOrder(Long orderId, Long itemId);
}
