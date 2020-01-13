package com.mysql.jpa.repo.onetomany;

import com.mysql.jpa.entity.onetomany.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}