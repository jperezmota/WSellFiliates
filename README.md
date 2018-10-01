# WSellFiliates
Is an easy to use Web Application integrated with your Wordpress Woocommerce Storefront in order to help you
build an Affiliate Program to boost your sales and give visibility to your members about their earned commission
made through their assigned Coupon Code.

<p align="center">
    <img src="https://s8.postimg.cc/68xq1rz6d/wsellfiliate.png" height="500" width="550" />             
</p>

## Who Uses WSellFiliates

* [TheCoder.store](https://www.thecoder.store/)

## Built With

* [Spring with Spring Boot](https://spring.io/projects/spring-boot) - The Web Framework used
* [Vaadin Framework](https://vaadin.com/) - Web Framework used for the UI.
* [MySQL](https://www.mysql.com/) - Database used
* [Flyway](https://flywaydb.org/) - Database Migration Tool
* [Maven](https://maven.apache.org/) - Dependency Management

## Install
#### Prequisites

* Java SE Development Kit 8
* MySQL 5.7.18+

#### Installation Process

1. Clone the repository to your directory
```
git clone https://github.com/jperezmota/WSellFiliates.git
```
2. Import the Maven project to your IDE

3. Create in your WordPress `Database` the following `View`
```
create view coupons_sales as 
select
    p.id as order_id,
    oi.order_item_name as coupon,
    p.post_date,
    max( CASE WHEN pm.meta_key = '_billing_country' THEN pm.meta_value END ) as billing_country,
    max( CASE WHEN pm.meta_key = '_billing_city' THEN pm.meta_value END ) as billing_city,
    max( CASE WHEN pm.meta_key = '_billing_state' THEN pm.meta_value END ) as billing_state,
    max( CASE WHEN pm.meta_key = '_paid_date' THEN pm.meta_value END ) as paid_date,
    sum( CASE WHEN pm.meta_key = '_order_total' THEN pm.meta_value END ) as order_total
from
    wp_posts p 
    join wp_postmeta pm on p.ID = pm.post_id
    join wp_woocommerce_order_items oi on p.ID = oi.order_id
where
    post_type = 'shop_order' and
    post_status = 'wc-completed' and 
    oi.order_item_type = 'coupon'
group by
    p.ID, 
    oi.order_item_name, 
    p.post_date
```
4. Set `app.commission.percentage` property for the Commission Percentage (in the following format 0.05 which means %5) in the `application.properties` file. Example below.
```
app.name = WSellFiliates
app.commission.percentage = 0.05
spring.profiles.active=dev
````
5. Set `Databases` urls in the `application-dev.properties` file.
```
##-------------------- DATASOURCES --------------------
# Primary (WSellFiliates)
spring.datasource.jdbcUrl = jdbc:mysql://localhost:3306/wsellfiliates?useSSL=false
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
# Wordpress
wordpress.datasource.jdbcUrl = jdbc:mysql://localhost:3306/wordpress?useSSL=false
wordpress.datasource.username=root
wordpress.datasource.password=root
wordpress.datasource.driver-class-name=com.mysql.jdbc.Driver

##--------------------- HIBERNATE ---------------------
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = validate
```

5. From the project directory run the following command: `./mvnw spring-boot: run`

6. Log In with: admin, for username and password.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/jperezmota/WSellFiliates/tags).

## Contributing

Please take a look at our [contributing](https://github.com/jperezmota/WSellFiliates/blob/master/CONSTRIBUTING.md) guidelines if you're interested in helping!

#### Pending Features

Pending features are listed here: [Issues](https://github.com/jperezmota/WSellFiliates/issues)

## Contact me

Feel free to contact me if you need any help! jonathanperezmota@gmail.com
