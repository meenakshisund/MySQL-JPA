# MySQL-JPA
Connect to MySQL with JPA

1. Start MySQL outside with your own schema name. 
2. Copy JDBC url, credentials and place in application.properties

### Student retreival from MySQL

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
	"name": "order_1",
	"items": 
		[
			{
				"itemId": "1001",
				"itemPrice": 100,
				"itemQuantity": 1
			},
			{
				"itemId": "1002",
				"itemPrice": 1000,
				"itemQuantity": 1
			}
		]
}
{
	"name": "order_2",
	"items": 
		[
			{
				"itemId": "1003",
				"itemPrice": 100,
				"itemQuantity": 10
			},
			{
				"itemId": "1004",
				"itemPrice": 1000,
				"itemQuantity": 10
			}
		]
}
```

Order(1): order_id is primary key
```java
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id_pk")
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<Items> items;
```

Items(*):
```java
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="items_id_pk")
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id_fk")
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

1. create table items (items_id_pk bigint not null auto_increment, item_id varchar(255), item_price double precision, quantity integer, total_price double precision, order_id_fk bigint, primary key (items_id_pk)) engine=MyISAM

2. create table orders (order_id_pk bigint not null auto_increment, name varchar(255), total double precision, primary key (order_id_pk)) engine=MyISAM

3. alter table items add constraint FK4e6v8g7h03f1n3cusx4vdti1f foreign key (order_id_fk) references orders (order_id_pk)

Hibernate: insert into orders (name, total) values (?, ?)
Hibernate: insert into items (item_id, item_price, quantity, order_id_fk, total_price) values (?, ?, ?, ?, ?)
Hibernate: insert into items (item_id, item_price, quantity, order_id_fk, total_price) values (?, ?, ?, ?, ?)

order_id_pk	name		total
1		order_1		1100
2		order_2		11000


items_id_pk	item_id	item_price	quantity	total_price	order_id_fk
1		1001	100		1		100		NULL
2		1002	1000		1		1000		NULL
3		1003	100		10		1000		NULL
4		1004	1000		10		10000		NULL

**@EnableCaching used and @Cacheable is used to cache orders and items by id.**

References:

1. https://howtodoinjava.com/spring-boot2/hibernate-configuration-example/
2. https://www.journaldev.com/2924/hibernate-one-to-many-mapping-annotation#hibernate-one-to-many-mapping-example-annotation
3. https://www.baeldung.com/jpa-joincolumn-vs-mappedby
