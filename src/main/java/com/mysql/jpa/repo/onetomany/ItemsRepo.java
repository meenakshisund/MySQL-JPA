package com.mysql.jpa.repo.onetomany;

import com.mysql.jpa.entity.onetomany.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepo extends JpaRepository<Items, Long> {
}
