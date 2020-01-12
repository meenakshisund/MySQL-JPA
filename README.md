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

**Order creation**
1. Aim was to find totalPrice of each itemId in payload and save it in DB. 
2. Sum up all totalPrices and populate, save Order level toatal Price in DB.

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

Order(1): order_id is primary key
```java

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id")
    private long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Set<Items> items;
```

Items(*):
```java
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @ManyToOne
    private Order order;
```

An order can have multiple items in it. Each Item has quantity and itemPrice. TotalPrice of each item is calculated and saved in DB using **@PrePersist** annotation in both entities.

Order:
```java
    @PrePersist
    public void findTotalPrice() {
        //total = items.stream().map(Items::getTotalPrice).reduce(0.0, Double::sum);
        total = 0;
        for (Items item: items) {
            total += item.getItemPrice() * item.getItemQuantity();
        }
    }
```

Items:
```java
    @PrePersist
    public void findTotalPrice() {
        totalPrice = itemPrice * itemQuantity;
    }
```

**Note:**
@PrePersist in Order entity did not work when all items totalPrice field values are summed up. Reason was Items will be saved to DB after Parent entity(Order) is persisted. Hence had to take all items qty and individual prices and summed up.

**@EnableCaching used and @Cacheable is used to cache orders and items by id.**

References:

1. https://howtodoinjava.com/spring-boot2/hibernate-configuration-example/
2. https://www.journaldev.com/2924/hibernate-one-to-many-mapping-annotation#hibernate-one-to-many-mapping-example-annotation
