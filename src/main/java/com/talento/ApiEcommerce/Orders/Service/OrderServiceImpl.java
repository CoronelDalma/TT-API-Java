package com.talento.ApiEcommerce.Orders.Service;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Articulo.Service.ArticuloService;
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

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ArticuloService itemService) {
        this.repository = repository;
        this.itemService = itemService;
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
}
