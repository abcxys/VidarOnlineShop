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

### April 18th changes:
Visio database design changes:
1. Add necessary fields to table 'dealers'. Add table 'dealer_types'.

Java/Spring side changes:
1. Add a web page for sales order creation, with customer/product information insertion.
2. Add path/pages constants to support sales ordering controller construction.
3. Add sales ordering controller method to SalesOrderController class.
4. Update quantity data type in 'CartItem' class.
5. Update client side web page and javascript to show SalesOrderItem object in Datatable.
6. Add SalesOrderRestController for Sales orders CRUD.
7. Add SalesOrderItem class to hold sales order product/quantity item for salesOrdering table.
8. Add CartService/Impl getCartItemsTable() method to retrieve DataTable for SalesOrdering webpage.
9. Update sql queries for dealer related initialization/queries.
10. Add entity/repository classes for Dealer.
11. Add dealer list retrieving methods in service/impl.
12. Add dealer_dict to model of salesOrdering web page.

### April 19th changes:
Javascript design gist:
1. Event trigerring: for example, textarea.val() would not directly trigger 'change' or 'input' event. Need to call the event manually like textarea.val().change();

Java/Spring side changes:
1. Adjust the javascript code for salesOrderTable, to hide the 'length' and 'info' part of DataTable.
2. Set the default date format inferred from bootstrap-datepicker to be 'yyyy-mm-dd'.
3. Add dealer select on 'change' action.
4. Add html sections for date/S.O.No./dealer info.
5. Apply auto-adjusting font size function to both textarea.
6. Restrict the second textarea to be readonly also.
7. Add getDealerInfoById method to SalesOrderRestController. Also add the necessary service/impl functions.
8. Add thymeleaf fragment for dealer select, with label and select on same line.
9. Restrict height of dealer name/address detail textarea to be 100px.
10. Add Warehouse entity class and repository interface.
11. Hide pagination section of salesOrder DataTable.
12. Add warehouse dict to the sales ordering web page.
13. Add warehouse dict service/impl methods.
14. Add switch between cart/salesOrdering page link by keeping two thymeleaf blocks on navbar simultaneously.

### April 20th changes:
Bug fixes:
1. duplicated sql constraints, incorrect table entries insertion.

Java/Spring side changes:
1. Update the database initialization/data insertion with regard to sales_reps.
2. Update visio database design. ('sales_orders' and 'sales_reps' table)
3. Add sales_rep entity/repository classes.
4. Add overridden toString() method for SalesRep entity class.
5. Add a \<h2\> thymeleaf block for text block 'Sales Orders'
6. Add service/impl methods for SaleReps list retrieval.
7. Add salesRep select thymeleaf block.
8. Add client/server-side data transfer of SalesRep list.

### April 22nd changes:
System design gist:
1. Design factory inventory update page before May 14th.
2. You have to use "Data" annotation to allow Java side class instance to be parsed on client side.

Java/Spring side changes:
1. Update attribute name of ajax 'type' to 'method'.
2. Add \<form\> for submitting the sales order with table of items.
3. Add submit button with icon at footer location.
4. Add salesOrderForm submission handler.
5. Add PostMapping method in SalesOrderRestController, for adding new sales order.
6. Restrict the resizable attribute of textarea in salesordering web page.
7. Switch Java class passed to client side. 'CartItem' to 'SalesOrderItem'.
8. Update data types of table 'sales_orders'.
9. Replace date class of SalesOrder class.

### April 23rd changes:
Database design changes:
1. Add sales_order_status table and corresponding attribute 'status_id' to 'sales_orders" table.

Java class design gist:
1. We cannot map json data directly from client-side to Java interface (FloorColorSize). So I create a request class to hold simple productId-quantity data.

Html web page design gist:
1. I tried to keep navbar 'fixed' or 'sticky' by setting the 'position' value in style. It will have to work with 'z-index: 1'. Moreover, the following div has to set its margin top.
2. Example of hiding/showing navbar menu on scroll.
https://www.w3schools.com/howto/howto_js_navbar_hide_scroll.asp

Java/Spring side changes:
1. Add SalesOrderRequest class to hold data submitted from client-side.
2. Add sales order service/impl class, and method for adding new sales order.
3. Update definitions for table 'sales_orders' and 'sales_orders_product'.
4. Rename 'FloorOrder' related class to 'SalesOrder'.
5. Update name of second \<select\> from 'dealer1' to 'warehouse'.
6. Make dealer-select and salesRep-select 'required' on sales-ordering page.
7. Add success/error messages for creating sales order.
8. Add a request class to hold Json data passed from client side.

### April 24th changes:
System design gist:
1. Return section design needed, based on packing slip.

Java design gist:
1. With ManyToMany annotation to define relationship between entites, we may encounter 'There is a cycle in the hierarchy!' error upon serializing data into JSON.
We want to use JsonIgnore to skip certain attributes that cause hierarchial recursion.
But it won't work with net.sf.json. So the easiest way to work around is to use ObjectMapper to convert Java List to JSON.

Database design changes:
1. Add boolean attribute release_ok to table 'Sales_Orders'.

Java/Spring side changes:
1. Update addSalesOrder serviceImpl method, use SalesOrderItemRequest class to hold table data from client-side.
2. Add service method execution coding to addNewOrder controller method.
3. Update client-side javascript code to organize data to transfer towards server-side.
4. Revert and update comment on salesOrders Set attribute.
5. Remove unnecessary PathConstants key SALES_ORDER.
6. Update text for 'addNewSOBtn' from 'Add' to 'Save'.
7. Add PostMapping("/add") for SalesOrderController, as counterpart of "/cart/add" mapping.
8. Add a conditional thymeleaf block to product page.
9. Apply @JsonIgnore annotation to attribute 'hardwoodfloors', to avoid json recursion.
10. Add customized json serializers for Dealer/SalesRep class objects.

### April 25th changes:
1. Update database UML design.
2. Add sql initialization queries for tables 'drivers', 'packing_status', 'packing_slips', 'sales_order_status'.
3. Update the return type of 'addSalesOrder' from 'MessageResponse' to 'ResponseEntity'.
4. Add sales-orders page upon click on 'order' in navbar.
5. Add page constant for sales-orders and GetMapping controller method.
6. Use 'first_name' attribute of SalesOrder to hold dealer name for Datatable display.
7. Add sales-order edit/update page for specific sales order id.
8. Add ManyToOne relationship to the 'warehouse' attribute of 'SalesOrder' class.
9. Update href link to 'ORDERS' navbar tab.
10. Add thymeleaf fragment for sales order 'dealer_address' and 'dealer_name' textarea.
11. Add url link for double-click on sales orders table to edit/update specific sales order.

### April 26th changes:
1. Add SalesOrderFilterConditionForm to hold filter data passed from client-side.
2. Update add_orders sql to test for sales_orders_products table display.
3. Add thymeleaf fragment for sales order date picker.
4. Update sales-orders client-side web page design and behavior.
5. Add sales orders filter service/impl/controller/repository methods.
6. Add Entity/Repository/Serializer for table 'sales_order_status'.
7. Add SalesOrderStatus/ReleaseOk \<select\> to sales order editing page.
8. Add a boolean \<select\> thymeleaf fragment for ReleaseOk status display.

### April 27th changes:
1. Update method of SalesOrderProductRepository, to return data type SalesOrderProduct.
2. Update server side code for retrieving SalesOrderProducts table for specific sales order id.
3. Update client side code to pass current sales order id for retrieving sales order products table.
4. Add 'addProductBtn' to sales-order page, to add new row entries to table.
5. Add a button fragment with type="button" to avoid submitting form.

### April 29th changes:
1. Update content of the first column of table salesOrdersTable.
2. Update inventory.html indentation.
3. Update sales-ordering page to match the entry deleting feature on sales-order page.
4. Add quantity_picked_up attribute to SalesOrderItem class.
5. Add sales order update messages.
6. Update client side design to correctly pass updated sales order data to server side.
7. Add a JPA query method to delete sales order products entry by sales order id.

### April 30th changes:
Database insert entry duplicated id error.
This kind of error will raise when I insert some entries manually by sql, then insert by JPA/Hibernate.

1. Add update sales order REST controller service/impl methods.
Note: each time sales order get updated, the sales order product entries will be deleted and then added again. No history will be maintained.
2. Add boolean 'active' field/attribute to SalesOrderProduct and corresponding table.
3. Add set inactive method to SalesOrderProductRepository.
4. Update table checkbox header, to make it align in the center.
5. Add table 'sales_orders_packing' for salesOrder/PackingSlip ManyToMany relationship.
6. Add entity classes for table 'packing_slips' and 'sales_orders_packing'.
7. Add 'createPackingSlipModal' modal for creating packing slip from salesOrderProducts.

### May 1st changes:
Java/Spring side changes:
1. Update the DataTable retrieval ajax methods from 'POST' to 'GET' for Sales Orders.
2. Adjust order of table initialization of table 'sales_orders_products'.
3. Fix the bug of adding new sales order.
4. Update the 'getFilteredSalesOrders' method of SalesOrderServiceImpl.
5. Add necessary fields/attributes to SalesOrderItem for creating packingSlip display.
6. Update client-side web design and javascript code for creating packing slips from sales orders.
7. Add server side repository/service/impl/controller methods for creating packing slips.

### May 2nd changes:
Java/Spring design gist:
1. When using JPQL with java.util.List as input argument, you need to wrap the argument with parenthesis in query
Example: "AND ((:statusIds) IS NULL OR orders.status.id IN (:statusIds))"

Java/Spring side changes:
1. Update sales-order web page, display date wanted on page request.
2. Add 'findFilteredPackableSalesOrders' method to SalesOrderRepository.
3. Update salesOrderServiceImpl to set salesOrder's dealer, date, dateWanted, salesRep, warehouse, status, releaseOk fields.
4. Update sales-order.js to retrieve updated data of statusId/releaseOk/dateWanted.
5. Update thymeleaf rendering of the boolean \<select\>.
6. Update client side web design and add a \<select\> for SalesOrderStatus. Make the status \<select\> multiple.
7. Add a multiple \<select\> for SalesOrderStatus selection.
8. Add String attribute 'statusIdsString' to hold and pass selection of 'salesOrderStatus' from client-side to server-side.
9. Add salesOrderStatus_dict to sales-orders web page model.
10. Update findFilteredPackableSalesOrders JPQL query to accommodate input argument statusIds.
11. Add entity class and repository interface for packing_status table.
12. Add entity class and repository interface for drivers table.

### May 3rd changes:
Java/Spring side changes:
1. Add server side controller/service/impl methods for drivers dict retrieval and transfer.
2. Update default packingStatusId and driverId value for PackingSlip entity class.
3. Add \<form\> with action to sales-orders page and driver selection on top of the form.
4. Add success/error messages for create/update packing slips.
5. Add an entry of packing slip status 'created'. 
6. Add entity class PackingSlipItem for table 'sales_orders_packing'.
7. Add REST controller/repository class for packing slip and also PackingSlipRequest class to hold data from client side.
8. Update PackingSlip class, replace packingStatusId and driverId with actual entity class.
9. Update javascript code of sales-orders page for editing packing slip creation.
10. Add addPackingSlip service/impl methods. Note that only packing slip is created, the associated 'sales_order_packing' entries are not added.
11. Add thymeleaf fragment for packing slip description textarea.
12. Add packing slip description html element  to client side.
13. Update PackingSlipRequest class to hold the packing slip description.
14. Add insertSalesOrderPackingItems method to Packing Service/Impl, for creating new entries for table 'sales_orders_packing'.