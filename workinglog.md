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

### What database table should I create to manage the "back order" inventory?

### March 22nd changes:
#### Database design visio file changes:
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

### March 23rd changes:
#### Bug fixes:
1. Click on "MY ACCOUNT", then click on "UPDATE" will redirect to url "user/update-inventory" which does not exist. Resolved by changing /href content in "common-fragments" from "update-inventory" to "/update". And also change the associated texts in the project from "update-inventory" to "update".

#### Database design visio file changes:

#### Java/Spring side changes:
1. Update "perfumes-fragments" string in the project to "product-fragments", and move the "inventory-sidebar" fragment to "product-fragments"
1. TODO: Add a page for adding new product
    Based on admin-add-perfume.html, change the components name to hardwood related product attributes.
2. TODO: If any of the attributes of new product does not exist, provide an option to add new attribute
3. TODO: Add a page for adding new inventory for a specific product
4. TODO: Restrict product table entry to be not duplicated for specific combination of several keys

#### There're two proven ways to concatenate dynamic text with static text in thymeleaf text
1. "'Enter the ' + ${name}"
2. "${'Enter the ' + name}"

### March 24th visions:
1. Develop IOS app with macbook air, with springboot backend(RESTAPI)
2. Develop wechat mini program, with springboot backend(RESTAPI)
3. Deploy springboot application with docker, and then to EC2 machine.

### March 25th changes:
#### Bug fixes:
1. Resolve issue caused by updating "WidthDict" to "SizeDict", which directs to 404 not found error on inventory page.
2. Fix the bug that carousel slide dot indicator not working.

#### Java/Spring side changes:
1. Create REST controller for product CRUD operations.
2. Adjust "AddNewProductBtn" css style and add "id" parameter to common icon button fragment.
3. Adjust the invalid location of "script" tag, has to be inside of "html" tag.

### March 26th changes:
#### Bug fixes:
1. Fix the bug that the "Check Stock" button pop up div shows wrong colour(css class).

#### Java/Spring side changes:
1. Add Jquery-bootbox support, to show alert box properly.
#### Command line dumping the database to plain text file, and restore
password is root
```shell
pg_dump -U postgres -W -F p -d hardwoodfloor > hardwoodfloor.sql
psql -U postgres hardwoodfloor < hardwoodfloor.sql
```
#### Database design visio file changes:
1. Add create_time/create_user_id, update_time/update_user_id to plank_colors table.

### March 27th changes:
#### Bug fixes:
1. <i> tag and corresponding text (My Account/Exit) does not display on the same line. Remove the "pr-5 pl-5" classes on <a> tags.
Note: this will still happen when shrink the page width, maybe the text is too wide for its parent element <a> tag. I try to use @Media to make fontsize smaller.
#### Java/Spring side changes:
1. Modify the common header, make the logo image smaller and move the location of the logo to top left corner.
2. Change <ul> tag class from "mr-auto" to "ml-auto" to make it float right.
3. Change text "MY ACCOUNT" to "ACCOUNT"
4. Update the reaction after press "addNewColour" button, pop up an alert bootbox showing necessary success information, reload the add new product page after predefined seconds of timeout.
5. Add create/update wood_species api on add-new-product page.
6. Remove interface 'ColorDict', entity class 'PlankColor' is enough to do the job.
7. Remove interface 'SizeDict', entity class 'PlankSize' is enough
8. Remove interface 'SpeciesDict', create new entity class 'WoodSpecies' to do the job
9. TODO: Add create/update api's to add-new-product page.
#### Database design visio file changes:
1. Add 'ware_layer_thickness'(with default value 2.0) and 'carton_weight' field to table hardwoodfloors
2. Add create_time/create_user_id, update_time/update_user_id to table hardwoodfloors
3. Add default value 'American' to field 'country' of table wood_species
4. Add create_time/create_user_id, update_time/update_user_id to table wood_species

### March 28th changes:
#### Bug fixes:
#### Java/Spring side changes:
1. Add "top_layer_thickness" aka "wear_layer_thickness" text input to add-new-product webpage.
2. Add dynamic display of product thickness in "size" section.
3. Add new function for adding new plank type with name and alias
4. Instead of passing list of strings for gradeDict/colourDict/sizeDict/speciesDict/plankTypeDict, pass list of entity objects
5. Remove Java class GradeDict, replace with class Grade. Install the counterpart functions in Product service instead of Inventory service.
6. Add new function for adding new grade with name and alias
#### Database design visio file changes:

### April 1st changes:
#### Java/Spring side changes:
1. Adjust web-fragment input type to "number" for sqftage info.
2. Add unique constraint for combination of (width, length, thickness, sqftage) in database initialization sql file.
3. Add PlankTypeRepository for PlankType related database queries.
4. Update the data type of squarefootPerCarton from "double" to "BigDecimal" and also restrict the precision and scale.
5. Update the data type of squarefoot_per_carton from "float(4)" to "decimal(10,2) to ensure data mapping: decimal -> BigDecimal
6. Add form submit with product request and binding results to add-new-product web page. Note: require web control 'name' field same as Java product request field name.
#### Data design visio file changes:
1. Add necessary "create_user_id"/"update_user_id", "create_time"/"update_time" fields to multiple tables.

### April 2nd changes:
#### Java/Spring side changes:
1. Add thymeleaf defined 'id' value to 'add-string-attribute-input','add-string-required-attribute-input', 'add-number-required-attribute-input'
2. Add th:selected clause to 'add-product-attribute-select', allowing value pass from ProductRequest instance.
3. Adjust the 'name' attribute in the 'add-product-attribute-select' fragment, make it align with the field name of ProductRequest, otherwise the value cannot be passed to backend.
5. Add application-prod.properties file for the control of production environment settings.
6. Adjust the frontend control names to align with the backend ProductRequest field names.
7. Add the add new product functions after input validations.

### April 3rd changes:
#### Java/Spring side changes:
1. Add GET mapping for 'getProducts' for listing of update all items.
2. Add getBatchName method to FloorColorSize interface.To get more information out of query for products.
3. Update unnecessary 'perfume' keywords in 'add-new-product.html'
4. Add a new fragment for submitting the product update.
5. Update style.css to adjust the location appearance of dropdown button.
6. Add multiple necessary thymeleaf fragments for product attributes.
7. Add active field in accordance with entity class.
8. Add page constant and message for updating product.
9. Add POST/GET mapping for updating product.
10.Add service/serviceimpl methods for updating product.

### April 4th changes:
#### Java/Spring side changes:
1. create a page for inventory manipulatioin.
2. Move the in-file defined javascripts to independent file.
3. TODO: design a pop-up modal for inventory update(include create and modification).

### April 5th changes:
#### Java/Spring side changes:
1. Update my IDE from Eclipse to IDEA ultimate, use the crack-patch for activation.
Note: the necessary crack file stored in D:/jetbra, don't delete this folder.