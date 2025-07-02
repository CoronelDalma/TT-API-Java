package com.talento.ApiEcommerce.Orders.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.talento.ApiEcommerce.Address.Model.Address;
import com.talento.ApiEcommerce.Customer.Model.Customer;
import com.talento.ApiEcommerce.OrderItem.Model.OrderItem;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();
    private Date creationDate;
    private Date completionDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order() {
    }

    public Order(List<OrderItem> items, Date creationDate, Date completionDate, OrderStatus status, Customer customer, Address deliveryAddress) {
        this.items = items;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
