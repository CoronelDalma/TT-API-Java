package com.talento.ApiEcommerce.Orders.Service;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Articulo.Service.ArticuloService;
import com.talento.ApiEcommerce.OrderItem.Model.OrderItem;
import com.talento.ApiEcommerce.OrderItem.Repository.OrderItemRepository;
import com.talento.ApiEcommerce.Orders.Model.Order;
import com.talento.ApiEcommerce.Orders.Model.OrderStatus;
import com.talento.ApiEcommerce.Orders.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository repository;
    private final ArticuloService itemService;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ArticuloService itemService, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.itemService = itemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order order) {
        // todo validaciones:
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return order;
        }
        order.getItems().forEach(item -> {
            item.setOrder(order);
            Long itemId = item.getArticulo().getId();
            Articulo fullItem = itemService.getArticuloById(itemId)
                    .orElseThrow(() -> new RuntimeException("Articulo no encontrado " + itemId));
            item.setArticulo(fullItem);
        });

        if (order.getId() == null) {
            order.setCreationDate(new Date());
            order.setStatus(OrderStatus.PENDING);
        }
        return repository.save(order);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<OrderItem> updateQty(Long orderId, Long itemId, int newQty) {
        Optional<OrderItem> itemOpt = orderItemRepository.findByOrderIdAndArticuloId(orderId, itemId);

        if (itemOpt.isEmpty()) return Optional.empty();

        OrderItem item = itemOpt.get();
        int stock = item.getArticulo().getStock();

        if (newQty <= 0) {
            orderItemRepository.delete(item);
            return Optional.empty();
        }
        item.setQty(Math.min(newQty, stock));

        return Optional.of(orderItemRepository.save(item));
    }

    @Override
    public Optional<OrderItem> addItemToOrder(Long orderId, Long itemId) {
        Optional<Order> orderOpt = repository.findById(orderId);
        Optional<Articulo> itemOpt = itemService.getArticuloById(itemId);

        if (orderOpt.isPresent() && itemOpt.isPresent()) {
            Order order = orderOpt.get();
            Articulo item = itemOpt.get();

            boolean exists = order.getItems().stream().anyMatch(i -> i.getArticulo().getId().equals(itemId));
            if (!exists) {
                OrderItem newItem = new OrderItem(order, item, 1);
                order.getItems().add(newItem);
                repository.save(order);
                return Optional.of(newItem);
            }
        }
        return Optional.empty();
    }
}
