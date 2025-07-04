package com.talento.ApiEcommerce.OrderItem.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Orders.Model.Order;
import jakarta.persistence.*;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    private Integer qty;

    public OrderItem() {
    }

    public OrderItem(Order order, Articulo articulo, Integer qty) {
        this.order = order;
        this.articulo = articulo;
        this.qty = qty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
