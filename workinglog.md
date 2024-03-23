### Why create four sequences when initializing DB in V1 statement?

### How to enter docker container and postgres command line tool?
```shell
docker exec -it postgresVidar bash
psql -U postgres
```

### Steps to initialize postgresql database
```sql
CREATE DATABASE hardwoodfloor;
ALTER USER postgres WITH PASSWORD 'root';
\c hardwoodfloor;         //Enter the database
\i /home/migration/V1__Init_DB.sql  //You need to move the scripts in docker system directory before running them
```
Run table initialization scripts in docker shell before running springboot

### Why Hibernate throws "Perfume not mapped" error when refract "Perfume" class to "HardwoodFloor" class?
Because Hibernate requires type name in SQL queries match the name of Java class.
Change ```SELECT perfume FROM Perfume perfume...``` to ```SELECT perfume FROM HardwoodFloor perfume... ```

### How to delete a database in POSTGRESQL? And what configuration to change if the database name is changed?
To delete a databased, ```drop database [name]```
If the database name is changed, change the setting in "application.properties"
```spring.datasource.url=jdbc:postgresql://localhost/[database name]```

### Hibernate JPA findById interface returns Optional class
To get entity object, use Optional object.get() to achieve.

### How to setup a login page with Spring Security?

### The loading order of Bootstrap, Bootstrap-select, Jquery?
Bootstrap-select must be loaded after Bootstrap, Jquery should be loaded before Bootstrap and only one verion of Jquery loaded.

### How am I suppose to show simultaneously "physical inventory" and "back order inventory" on web page at the same time?
For dealers and general public, just show the available date according to their requested quantity of the product.(Regardless of the batch number.)

### The sequence order of creating product/product-attributes/container/inventory?


### March 22nd changes:
#### Database visio file changes:
1. Add "users", "user_role", "role", "permission", "role_permission" tables
2. Unify "created_at", "last_modified_at" key names to "create_time", "update_time"
#### Java/Spring side changes:
1. Change title of the second tab on home page to "PRODUCTS".
2. Update all the package name prefix from "com.gmail.merikbest2015.ecommerce" to "vidar.websystem"
3. Update test part package name prefix from "com.gmail.merikbest2015.ecommerce" to "vidar.websystem"
4. Update text messages in home.html and contact.html accordingly
5. Update phrase "perfume" in controller/service/serviceImpl to "product"
6. Add SF/CTN information on product page
7. Add an estimated total sqftage line to single product page, also add a sqftage input control to single product page. Add the associated js functions.
6. TODO: Add a "find a dealer" page to allow customers to search in website embeded map

### What database table should I create to manage the "back order" inventory?