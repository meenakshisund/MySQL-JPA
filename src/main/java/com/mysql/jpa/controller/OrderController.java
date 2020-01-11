package com.mysql.jpa.controller;

import com.mysql.jpa.entity.Items;
import com.mysql.jpa.entity.Order;
import com.mysql.jpa.repo.ItemsRepo;
import com.mysql.jpa.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemsRepo itemsRepo;

    /* POST body

    {
	"name": "order_15",
	"items":
		[
			{
				"itemId": "15001",
				"itemPrice": 1500,
				"itemQuantity": 10
			},
			{
				"itemId": "17002",
				"itemPrice": 11000,
				"itemQuantity": 10
			}
		]
    }

     */

    @PostMapping(value = "/order")
    public Order createOrder(@RequestBody Order order) {
        return orderRepo.save(order);
    }

    @Cacheable("order")
    @GetMapping(value = "/order/{id}")
    public Order getOrder(@PathVariable("id") String id) {
        return orderRepo.getOne(Long.valueOf(id));
    }

    @GetMapping(value = "/order/all")
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Cacheable("items")
    @GetMapping(value = "/items/{id}")
    public Items getItem(@PathVariable("id") String id) { return itemsRepo.getOne(Long.valueOf(id)); }

    @GetMapping(value = "/items/all")
    public List<Items> getAllItems() {
        return itemsRepo.findAll();
    }
}