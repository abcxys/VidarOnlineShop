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

### What database table should I create to manage the "back salesOrder" inventory?

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
1. \<i\> tag and corresponding text (My Account/Exit) does not display on the same line. Remove the "pr-5 pl-5" classes on \<a\> tags.
Note: this will still happen when shrink the page width, maybe the text is too wide for its parent element \<a\> tag. I try to use @Media to make fontsize smaller.
#### Java/Spring side changes:
1. Modify the common header, make the logo image smaller and move the location of the logo to top left corner.
2. Change \<ul\> tag class from "mr-auto" to "ml-auto" to make it float right.
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
2. Fix the functionality of button 'searchProductInventoryBtn'
3. Update web page for product inventory display.
4. Update the frontend filtering controls.
5. Update the dev-testing sql queries.
6. Add a hidden column of floorId, and pass the floorId data to process of rendering 'editInventoryModal'.
7. Add backend support for requesting ProductInventory table. Rename InventoryItem class name to ProductInventoryItem.
8. Fix the bug that add new product-attribute modal not showing.

### April 6th changes:
Observed a bug in ProductServiceImpl:
When add a new product, HardwoodFloor floor mapped from ProductRequest does not have a 'id' field.
1. Add database connection to IntelliJ IDE.
2. Add new Java class template to IntelliJ.
3. Add findInventoryItemByProductId query to InventoryRepository class.
4. Fix the bug that when you try to add new product, error reports.

The reason is that the modelMapper mapped HardwoodFloor does not have any 'id' field. And the business logic here is incorrect. We already have a tab for updating product, so don't process any product update in 'add-new-product' tab. Check duplicity by querying combination of (color, size, type, grade, species, batchNumber).

### April 8th changes:
1. Update preventative measures for AWS account abcxys92@gmail.com.
Note that: GuardDuty service stated "30-day free trial" upon activation. Keep track of AWS cost in the future.
2. Update the postgres database logging settings:
Use commands to check status: 
```postgresql
show logging_collector;
show log_directory;
show log_filename;
```
Use commands to change status:
```postgresql
alter system set logging_collector='on';
alter system set log_directory='/var/log/pg_log';
```
Note that log_directory is relative directory under postgresql/Data, we will create a directory and give access to postgres
```shell
mddir -p /var/log/pg_log
chown postgres:postgres /var/log/pg_log/
chmod 700 /var/log/pg_log/
```
Java/Spring side changes:
1. Change the default value of 'active' field of ProductRequest.
2. Pass user object to ProductService/Impl.
3. Add 'create_time/user', 'update_time/user' fields to HardwoodFloor class.
4. Add conditions for adding/updating product.
5. Minor change to apply service return value to ResponseBody.
6. Change the upload file size restriction in application property file.
7. Update the web page layout of InventoryItem with DataTable.
8. Update the frontend InventoryItem table behavior.

### April 9th changes:
In markdown file:
1. keep \<a\>, \<i\>, \<ul\> as is in rendered markdown files, using backslash as escape.

Java/Spring side changes:
1. Add InventoryItemRequest class to hold data passed from /add-new-inventory api.
2. Add create/update user field to Inventory entity class.
3. Add Location entity class.
4. Add LocationRepository interface.
5. Adjust the datatype of quantity in InventoryItemRequest.
6. Add frontend/controller/service solution for adding a new entry of inventory.
7. Add generating sequence for locations.

### April 10th changes:
Bug discovered:
'/update/products' -> Update item info -> Search, click on the button will redirect to wrong page.
Java/Spring side changes:
1. Add update inventory related service/serviceImpl methods
2. Add update inventory rest controller method for PutMapping url "/inventory/update"
3. Add subtable DataTable definition in Javascript.
4. Add location_id/description fields to InventoryEvent entity.
Note: TODO:inventory_event table and entity will have to add further fields/attributes, for example, foreign keys for container_id/order_id etc.
5. Add private method for adding inventory event to database.
6. Add JpaRepository interface for InventoryEvent.

### April 11th changes:
Javascript gist:
1. To render selectpicker dynamically with data passed from backend, you can use ajax/RESTful API and pass the rendering node to js function.

Java/Spring side changes:
1. Update the hyperlink ordering in product-fragments::update-sidebar.
2. Add GetMapping method for 'add-new-container' and the Pages constant.
3. Add GetMapping method for returning a list of product summary to frontend \<select\> control.
4. Add web page design and javascript functions to render add-new-container page.
5. Change 'getProductDict' to 'getActiveProductDict'.
6. Add initialization sql for table 'container_status', 'container', 'container_floors'.

### April 12th changes:
Dashboard alert design gist:
Factory manufacturing alert and factory inventory release alert design:
1. on-hand inventory lower than specific quantity && factory inventory greater than specific quantity, alert to release factory inventory.
2. on-hand inventory + factory inventory lower than specific alert, alert to factory manufacturing.

Javascript gist:
1. In HTML, if a \<button\> element is placed inside a \<form\> element without specifying the type attribute, it defaults to type="submit". So you need to explicitly assign the type of button if you don't want it to submit form upon click.

Database design gist:
1. Set automatically update expire date for salesOrder, with help of a designed field/attribute specifying the lengthe of validity.

Java/Spring side changes:
1. Drop the 'required' attribute of two numeric inputs for 'skid' and 'box'.
2. Add ContainerRequest class to hold data from client side.
3. Add PostMapping method for '/update/add-new-container'.
4. Add dependency for bootstrap-datepicker and the thymeleaf fragment.
5. add bootstrap-datepicker thymeleaf fragment.
6. Adjust the data type of attribute 'quantity' of table 'container_floors'

### April 13th changes:
Java/Spring side changes:
1. Add 'required' attribute to form elements.
2. Add javascript function to handle containerForm submission event.
3. Add 'Container' entity class to hold data for table 'container'.
4. Adjust 'ContainerRequest' class to include ProductContainerItem list.
5. Adjust the name of getActiveProductDict endpoint url.
6. Add update-containers web page with Datatable of Container information.
7. Add javascript file for rendering of container Datatable. (Require further adjustment for at least 'ETA' column and 'Status' column).
8. Add two repositories for 'Container' and 'ProductContainer'.
9. Add REST controller for container-related web requests.
10. Add container-related service/implements to InventoryService/Impl.
11. Update constants/fragments with regard to Containers.
12. Adjust annotation for ContainerRequest to accommodate for contentType application/json.
13. Add necessary field/attributes for ProductContainerItem and successMessage constants.

### April 15th changes:
Javascript gist:
Setting datatable cell value, must comply to the ColumnDef data type.

Bug fixes:
1. New container 'estimatedArrivalDate' not written into database. 
Reason, the attribute keys are different on client/server sides.

Java/Spring side changes:
1. Add REST api for update container info and retrieve container product info.
2. Add success message for updating container.
3. Add GetMapping for retrieving filtered container info to update.
4. Add native ProductContainerRepository query for retrieving container product info.
5. Update update-container web page, add necessary pop up modal for container product.
6. Add service/impl methods for retrieve/update container info and retrieving container product info.
7. Update javascript code for rendering two tables in container web page.

### April 16th changes:
Shell gist:
1. When encountered "Port 8080 was already in use" scenario, try to use the following shell command
```shell
 netstat -ano | findstr 8080
 taskkill /F /PID ***
```
Sometimes it still does not work, you can only restart your computer.
2. Javascript gist:
For DataTable ajax data argument injection, if the data needs to update every time on ajax trigger, use function instead of JSON like return type.
```Javascript
data: function(){
    return {
        searchType: $('#searchType').val(),
        searchValue: $('#searchValue').val()
    }
}
```

Bug fixes:
1. Column 4 with selectPicker sometimes not showing up properly on refresh. Add an on-draw function to the DataTable to forcefully activate selectPicker.
2. Duplicated queried result from findStockByFloorId, updated the native query to return sum of multiple stocks.

Java/Spring side changes:
1. Make 'skid' and 'box' columns editable and only take numerical inputs.
2. Add a column to show the productId of ProductContainerItem, with a link to the product details.
3. Activate the container search form functionality.
4. Update the javascript code to make the container updating functions normally.
Note: the update of \<select\> inside datatable requires extra care with the inner html coding.
5. Add client-side entry point and server-side PutMapping method for container item update.

### April 17th changes:
Java/Spring side changes:
1. Add service/impl method for update container product item.
2. Move link for "Update factory inventory" towards bottom.
3. Fulfill the functionality of updateContainerItem controller method.
4. Add a two-second reload timeout to bootbox alert of successful update of container product item.
5. Update sql files:
    1. Rename table 'store' to 'dealers'
    2. Rename table 'orders' attribute 'store_id' to 'dealer_id'
    3. Rename table 'orders' to 'sales_orders'
    4. Rename table 'orders_hardwoodfloors' to 'sales_orders_hardwoodfloors'
    5. Rename table 'sales_orders_hardwoodfloors' attribute 'order_id' to 'sales_order_id'
6. Conform thymeleaf fragment method name to the change of HardwoodFloor class.

Visio database design changes:
1. Rename table 'store' to 'dealers', rename table 'orders' attribute 'store_id' to 'dealer_id'
2. Rename table 'orders' to 'sales_orders'