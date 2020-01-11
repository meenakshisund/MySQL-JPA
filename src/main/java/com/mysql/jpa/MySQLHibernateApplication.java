package com.mysql.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MySQLHibernateApplication {
	public static void main(String[] args) {
		SpringApplication.run(MySQLHibernateApplication.class, args);
	}
}