# MySQL-JPA
Connect to MySQL with JPA

1. Start MySQL outside with your own schema name. 
2. Copy JDBC url, credentials and place in application.properties

# Student retreival from MySQL

**Student Creation**

POST: "/student"
```json
{
	"id": 3,
	"firstName": "Srini",
	"lastName": "Ravi",
	"emailAddress": "sriniUPDATE@gmail.com"
}
```

# OneToMany relation: Order(1) -> Items(*)

An order can have multiple items in it. Each Item has quantity and itemPrice. TotalPrice of each item is calculated and saved in DB using @PrePersist annotation. 

**Order creation**

POST: "/order"
```json
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
```

**@EnableCaching used and @Cacheable is used to cache orders and items by id.**
