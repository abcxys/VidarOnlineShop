# :hibiscus: Vidar Design Flooring Web Inventory Management System

To debug this project, first git clone to local. Then pull a postgres image and run container: 
```shell
docker pull postgres:12
docker run -d --name postgresVidar -p 5432:5432 -e POSTGRES_PASSWORD=pass123 postgres:12
```
And create database and change password of user 'postgres'
```sql
CREATE DATABASE hardwoodfloor;
ALTER USER postgres WITH PASSWORD 'root';
```

This E-commerce project built on Spring Boot.<br>
Deployed on Amazon web services EC2.<br>
See demo below.

User credentials:<br>
Login: test123@test.com <br>
Password: admin

Admin credentials:<br>
Login: admin@gmail.com <br>
Password: admin

## Used Technologies:

* Spring (Boot, Data, Security)
* JPA / Hibernate
* PostgreSQL
* Thymeleaf
* Bootstrap, CSS
* Maven
* Junit, Mockito
* Lombok

## About This Project
* Customers can register/login.
* Customers can navigate through the main pages, view products, or select a product from a storefront and preview.
* Customers can navigate on the main pages browse products or select an item from the showcase and preview.
* Customers can search for the product according to the specified criteria.
* Customers can add products to the shopping cart and delete products from the shopping cart.
* Customers can order the products in the shopping cart.
* Customers can change their password and view their orders.
* Admin can add or modify product.
* Admin can change the data of any user.
* Admin can view orders of all users.

## Screenshots

Menu page      |  Product page
:------------------------:|:-------------------------:
![Menu page](https://i.ibb.co/rkSZ5Qq/1.png)  |  ![Product page](https://i.ibb.co/4jk3R0s/2.png)

Cart  |  Ordering
:------------------------:|:-------------------------:
![Cart](https://i.ibb.co/mBKP5F3/3.png)  |  ![Ordering](https://i.ibb.co/WVRdRW1/4.png)

Email template  |  List of orders
:------------------------:|:-------------------------:
![Email template](https://i.ibb.co/bmKTLPJ/email-template.jpg)  |  ![List of orders](https://i.ibb.co/Rp21f3k/5.png)

User profile page  |  Add perfume page
:------------------------:|:-------------------------:
![User profile page](https://i.ibb.co/Fz1dB7L/6.png)  |  ![Add perfume page](https://i.ibb.co/ykX4hcG/7.png)