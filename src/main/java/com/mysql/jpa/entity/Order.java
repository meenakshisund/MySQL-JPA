package com.mysql.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="ORDERS")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id")
    private long id;

    @Column(name="total")
    private double total;

    @Column(name="name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Set<Items> items;

    @PrePersist
    public void findTotalPrice() {
        //total = items.stream().map(Items::getTotalPrice).reduce(0.0, Double::sum);
        total = 0;
        for (Items item: items) {
            total += item.getItemPrice() * item.getItemQuantity();
        }
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getTotal() {
        // return items.stream().map(Items::getTotalPrice).reduce(0.0, Double::sum);
        // above statement works as getTotal() is called by Jackson after save() is done.
        // before save(), value of totalPrice is always default value(0.0)
        return total;
    }
    public void setTotal(double total) { this.total = total; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Items> getItems() { return items; }
    public void setItems(Set<Items> items) { this.items = items; }
}