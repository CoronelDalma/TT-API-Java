package com.talento.ApiEcommerce.Orders.Controller;

import com.talento.ApiEcommerce.OrderItem.Model.OrderItem;
import com.talento.ApiEcommerce.Orders.Model.Order;
import com.talento.ApiEcommerce.Orders.Service.OrderService;
import com.talento.ApiEcommerce.Orders.Service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return service.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        try {
            Order newOrder = service.save(order);
            if (newOrder.getId() != null) {
                return ResponseEntity.ok(service.save(order));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> updateItemQty(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody int newQty) {
        OrderServiceImpl.UpdateResult result = service.updateQty(orderId, itemId, newQty);
        return switch (result) {
            case UPDATED -> ResponseEntity.ok().build();
            case DELETED -> ResponseEntity.noContent().build();
            case NOT_FOUND -> ResponseEntity.notFound().build();
        };
    }

    @PostMapping("/{orderId}/items/{articuloId}")
    public ResponseEntity<OrderItem> addItem(
            @PathVariable Long orderId,
            @PathVariable Long articuloId) {
        return service.addItemToOrder(orderId, articuloId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.getOrderById(id).isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
