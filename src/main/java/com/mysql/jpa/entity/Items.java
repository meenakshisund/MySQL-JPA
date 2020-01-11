package com.mysql.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ITEMS")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Items implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="item_id")
    private String itemId;

    @Column(name="item_price")
    private double itemPrice;

    @Column(name="total_price")
    private double totalPrice;

    @PrePersist
    public void findTotalPrice() {
        totalPrice = itemPrice * itemQuantity;
    }

    @Column(name="quantity")
    private int itemQuantity;

    @JsonIgnore
    @ManyToOne
    private Order order;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public double getItemPrice() { return itemPrice; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice){ this.totalPrice = totalPrice; }

    public int getItemQuantity() { return itemQuantity; }
    public void setItemQuantity(int itemQuantity) { this.itemQuantity = itemQuantity; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
