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

### May 4th changes:
Java/Spring side changes:
1. Add repository interface for PackingSlipItem, aka table 'sales_orders_packing'.

### May 5th changes:
1. Add javascript code for 'packing' web page. Two new feature of the Datatable: 1. row details section with .dt-control class. 2. colorization of the bootstrap-select.
2. Update PackingSlip class, replace packingStatusId and driverId with entity classes, add dealer info as attribute.
3. Update the database initialization query. 
4. Add serializers for PackingStatus and Driver.
5. Update html web design for packing page.
6. Update the getBeginOfDate function to be public static, easier to be reused.
7. Add a big header to sales-orders page.
8. Update javascript ajax call. Transfer dealer's company name for creation of packing slips.

### May 6th changes:
Bug fixes:
1. The select picker disappears upon click on 'searchPackingSlipBtn'.
Solution: add a call to selectpicker() function upon table.draw event.
2. The colorization function is not triggered upon refresh of the page. 
Solution: add the handler function to 'loaded.bs.select' event.
3. Click on select picker will also trigger expansion of packing slip details.
Solution: add a filtering condition on the event triggering, make sure the click is not on the select picker/checkbox/editPacking.
4. Select picker of packing status does not show correctly the actual status of packing slip.
Solution: update selectHtml of packings.js, to make the correct option 'selected', update serializer for PackingStatus to return id as number.

Java/Spring side changes:
1. Update PackingSlipRequest class to hold dealerCompany info. Add find dealer by company name method to DealerRepository.
2. Add repository/service/serviceImpl/REST controller methods for rendering filtered packing slip table. Also add PackingStatusDict retrieval service/impl methods.
3. Update PackingSlipRequest class to hold dealerCompany info. Add find dealer by company name method to DealerRepository.
4. Add dealer_dict and packingSlipStatus_dict to model of packing page. To enable packing slip filtering.
5. Replace the first column of packingSlipTable dt-control with checkbox (for the purpose of creating returns). 
6. Add edit button for the last column of packingSlipTable.
7. Update packing page html code to allow overflow-y.
8. Update javascript code for packing page to GET packing slip items to show in details table.
9. Update server side code repository/service/impl/REST controller for GET packing slip items.
10. Add client-side ajax function and server-side service/impl/controller methods for updating packing slip's status.

### May 7th changes:
Bug fixes:
1. The datepicker does not work on date change, the date value always keep the same.
Solution: remove the 'data-provide' attribute in div.
2. The datepicker for endDate returns the Date value of year 1900.
Solution: update the javascript code for endDate value assignment.
3. Infinite recursion HardwoodFloor['salesOrders'] -> SalesOrder['users'] -> User['perfumeList']...
Solution: put @JsonIgnore annotion on either 'perfumeList' field of User or 'user' field of SalesOrder

Java/Spring side changes:
1. Update repository/serviceImpl for PackingSlip filtering. To allow pagination on server side.
2. Update the naming of 'packing' to 'packings' for packing slips display.
3. Add client side page design/javascript code for single packing slip, and the page constant.

### May 8th changes:
Javascript design gist: packing.js
When I add row to datatable on client-side only, the correct way to access cell data is by querying \<tr\> \<td\>
``` javascript
// Assuming you have the row index
var rowIndex = 0; // Change this to the desired row index

// Select the specific \<tr\> element
var row = $('#packingSlipProductsTable tbody tr').eq(rowIndex);

// Get the value of the input element in the second column (index 1)
var quantityValue = row.find('td:eq(1) input').val();

// Get the value of the select element in the third column (index 2)
var productValue = row.find('td:eq(2) select').val();

console.log("Quantity value:", quantityValue);
console.log("Product value:", productValue);
```

Database UML design:
1. Add return related database tables to visio file.

Bug fixes:
1. The datepicker in single packing slip page does not initialize correctly(format, autoclose etc).
Solution: Remove datepicker initialization adn update it in add_new_container.js
2. The dropdown menu for product selection is too long and extruding part will be clipped at table boundary.
Solution: Set 'overflow' property of .dataTable_scrollBody to be visible.

Java/Spring side changes:
1. Add service/impl/controller methods for single packing slip page.
2. Add a new thymeleaf fragment for datepicker in single packing slip page.
3. Also update packing page html to avoid extruding dropdown menu clipping.
4. Update packing.js for the client-side PackingSlip item.

### May 9th changes:
Javascript design gist:
Sometimes the header of Datatable would not align with body. Especially when the table is inside a initially hidden modal.
We have two solutions here:
one is to call ```dataTable.columns.adjust()``` after table initialization.
second is to initialize dataTable upon modal shown.

Bug fixes:
1. Date wanted selection not working on sales-ordering page.
Solution: Another fucking TYPO!!! replace 'dataWanted' with 'dateWanted'.

Java/Spring side changes:
1. Add database return-related tables initialization queries.
2. Update ResponseEntity message of createPackingSlip controller.
3. Update javascript code for sales-orders page, add datepicker initialization attributes and response bootbox messages for creating packing slips.
4. Add modal for creating return slips on client side.
5. Update 'add-datetime-required-attribute-input' fragment, remove 'data-provider' attribute to allow manually initialization.
6. Update PackingSlipRequest class, make the default setting of PackingStatus value within the class itself.
7. Add a constructor with attribute 'id' to SalesOrderItem.
8. Add double click event handler to packingSlipTable row.
9. Add service/impl/REST controller methods for updating packing slip.
10. Update the packingSlip update form name, and the DataTable dataSrc JSON data formating allow mapping towards SalesOrderItem class.

### May 10th changes:
Java/Javascript coding gist:
Be careful of Date transfer to client-side. Compare container.js and returns.js, you can see that when we use ObjectMapper to transfer Datatable onto client-side, the Date Java class would be left with only the milisecond value.

Bug fixes:
1. Thymeleaf fragment "add-dealer-multiple-select" has a redundant option that affect selection.
Solution: remove the first option.

Java/Spring side changes:
1. Add two fundamental entries for table 'return_status'
2. Add success/error messages for ReturnSlip create/update.
3. Add Pages/PathConstants static final variables for returns web page.
4. Update client-side html/javascript code for packing page, accommodate for creating return slips.
5. Add serializers for PackingSlip/ReturnStatus classes, to avoid JSON recursion.
6. Add repository interfaces for ReturnSlip/ReturnItem.
7. Add repository interfaces for ReturnStatus.
8. Add ReturnSlipRequest class for holding Return Slip data transfer from client-side.
9. Add service/impl methods for 'create' and 'read' database operations of ReturnSlip.
10. Add controller/rest controller methods for return slips related request mapping.
11. Add client-side html/javascript code for returns.

### May 11th changes:
Java class design gist:
1. For multiple entity class that share a decent number of attributes/fields, can we let them extends a father class so that we don't need to define the repeated attribtues?
Pay attention to "@MappedSuperClass", this father class cannot be mapped to any table.

Java/Spring side changes:
1. Update javascript and REST controller code for methods of 'POST' to 'GET'.
2. Add client side html/javascript code for displaying/updating factory inventory.
3. Add update Page constant for update factory inventory. 
4. Add update factory inventory link to thymeleaf 'update-sidebar' for 'update' page.
5. Add controller method for retrieving update factory inventory page/model-view.
6. Add repository/service/serviceImpl/REST controller for factory inventory DataTable retrieval.
7. Update entries to table 'warehouse' and 'locations' for factory inventory foreign key of 'location_id'
8. Add success message constants for factory inventory add/update.
9. Add entity classes for table 'factory_inventory' and 'factory_inventory_event'.
10. Add repository interfaces for table 'factory_inventory' and 'factory_inventory_event'.
11. Add service/serviceImpl methods for factory inventory add/update.
12. Add controller and REST controller methods for factory inventory add/update.
13. Add mapping request path constant for factory inventory REST controller.
14. Update javascript file for update-factory-inventory. Disable editing of the 'location' field in DataTable. Also update the url link for updating factory inventory.


### May 13th changes:
Windows development environment, Replace environment variable JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr' with 'C:\Program Files\Java\jdk1.8.0_131'

EC2 deployment gist:
1. Create ec2 instance at and use ssh to connect 54.196.134.96
```shell
ssh -i ".\vidar-key.pem" ec2-user@54.196.134.96
```
2. Install docker on EC2 instance reference to https://www.jianshu.com/p/b5c800177baf
3. Add security group setup for POSTGRESQL connection to EC2 instance.
4. Install java 1.8 with ```sudo -i yum install java-1.8.0-openjdk-devel.x86_64```
5. Add custom TCP rule for port 8080 to security groups of EC2.
6. You can use Amazon Route 53 service to register for customized domain
7. To stop springboot application
```shell
ps aux | grep OnlineShop-1.0-SNAPSHOT.jar
kill -9 pid
```
8. To start springboot application
```Shell
nohup java -jar OnlineShop-1.0-SNAPSHOT.jar &
```

9. Restart docker service on EC2 reboot
```Shell
sudo service docker start
docker start postgresVidar
```
10. In MvcConfiguration.java replace '.addResourceLocations("classpath:/uploads/");' with '.addResourceLocations("file:/home/ec2-user/uploads/");'
11. Update application-prod.properties for upload.path=/home/ec2-user/uploads/
12. To find the pid of currently running application
```Shell
ps aux | grep OnlineShop-1.0-SNAPSHOT.jar
```

13. Set inbound rule for EC2 instance for remote login of postgres. ip 
Inbound rule 4 Source
97.111.246.106/32


Java/Spring side changes:
1. Update product not found text from "Perfumes not found" to "Products not found".
2. Add datepicker initialization to enable calendar dropdown for 'dateWanted' datetime control.
3. Remove unnecessary upload images.
4. Update ErrorMessage content of PRODUCT_NOT_FOUND.
5. Update the behavior of clicking on td.editor-delete button, align with the controls in packing.js
6. Update selectpicker dropdown menu appearance, enabling scrolling in vertical direction while set maximum height to be 250px.
7. Update application-prod.properties for the upload path on EC2 instance.

EC2 springboot on pid 13853

### May 14th changes:
Sales order design gist:
'DateWanted' attribute of SalesOrder should be automatically filled with the nearest container arrival date.

1. Update field/attribute names of SearchRequest class.
2. Update client-side design for sales ordering page. Add 'Add product' button to add products on same page instead of add to sales order from product page.
3. Update 'update-inventory' page to remove excess empty space around the table.
4. Remove the last column of the inventory table. Also update the top filtering form.
5. Remove sidebar on 'products' page for now. Because the checkbox filtering portion of SearchRequest is not working properly.
6. Update tableData formation of sales ordering. Also update 'step' of number 'input' in sales-order.js and sales-ordering.js
7. Add json serializers for PlankColor, PlankSize, PlankType, WoodSpecies, Grade, for Jsonifying HardwoodFloor class objects.
8. Update HardwoodFloor class, remove FloorColorSize class and update all related repository/service/impl/controller methods and thymeleaf templates and client side javascript.
9. Replace 'perfume' related text on server side with 'product'

### May 15th changes:
Java/Spring side changes:
1. Add grade alias display to product item card.
2. Adjust serviceImpl method to accommodate for the HardwoodFloor class refraction.
3. Add resourceLocations on AWS EC2 instance for handler of ("/img"/**).
4. Update product-item name and content, remove unnecessary \<span\> tags.
5. Add condition on popular items section on home page.
6. Update product html code with more product details and a section for product inventory/orders/containers. Also move javascript code to separate .js file.
7. Add an attribute 'VIA' to PackingSlip table/object.
8. Update REST controller and javascript code for sales orders, accommodating the update of HardwoodFloor class and SalesOrderItem class.
9. Update javascript code for packing/{id} page, accommodate for the update of HardwoodFloor and SalesOrderItem class.
10. Add entity/serializer/repository classes/interface for table 'shipping_methods'.
11. Add shipping method attributes to PackingSlip and PackingSlipRequest classes.

pid= 12695

### May 16th changes:
Bug fixes:
1. time search of orders failed.
Cause: the default timezone of EC2 machine instance is UTC, and springboot does not make changes to timezone
Solution: a. Change the timezone of EC2 machine with command ```sudo timedatectl set-timezone Canada/Eastern``` b. Add TimeZoneConfig class to set default timezone of Java.util.Date to be 'America/Toronto'
2. default release_ok value of orders upon creation.
3. update orders failed.

Java/Spring side changes:
1. Update html/javascript code for 'packing' page, for the selection of shipping method 'via'.
2. Add shipping method dictionary controller support and selection on sales-orders page.
3. Fix a typo of bootboxAlertPrompt
4. Fix the remaining impacts by replacing FloorColorSize class with modified HardwoodFloor class.
5. Fix the no serializer error from 'clicking on returnSlipTable row'.
Cause: not sure, because ReturnServiceImpl method 'getSalesOrderReturnItemsByReturnSlipId' is essentially the same as PackingServiceImpl method 'getSalesOrderPackingItemsByPackingSlipId'.
Solution: Add serializers to ReturnItem class attributes, and replace return type of getSalesOrderReturnItemsByReturnSlipId from 'SalesOrderItem' to 'ReturnItem'.

### May 17th changes:
1. Move the event handlers in product.js into document ready function to make it functions normally.
2. Update javascript code for sales-ordering page to accommodate for removal of FloorColorSize class. Also update code for delete button to make it work properly.
Note: 'aoColumns' attribute would not work with DataTable loading, all data would return 'undefined'.
3. Update SalesOrder REST controller method getSalesOrderItems to fix Json infinite recursion error.
4. Add Pages constant, service/impl/controller method for single ReturnSlip page.
5. Update 'update-product' page thymeleaf fragment value to accommodate for the replacement of 'FloorColorSize' class with 'HardwoodFloor'.
6. Add error message for "PackingSlip not found" and corresponding responses from service implement method.
7. Add error message for "ReturnSlip not found" and corresponding responses from service implement method.
8. Add a table 'cart' to database visio design file.

### May 20th changes:
1. Add client side html/javascript code for return web page.
2. Add a queuing page to show the status of queuing line.

### May 21st changes:
1. Add table 'test_packing_slips' for simple queuing status page setup.
2. Update Pages/Path constants for queuing, and add basic get controller method for queuing page.

### May 22nd changes:
1. Add javascript code for queuing page: Separate html code and javascript code.
2. Rename table 'test_packing_slips' to 'test_queuing'.
3. Add entity and repository class/interface for QueueItem.
4. Correct data type of packingSlipNo in QueueItem.

### May 23rd changes:
1. Update javascript for queuing page, add support for dragging on mobile devices.
2. Update table and entity field 'packingSlipNo' type from "Long" to "String".
3. Add REST controller/service/impl methods and javascript code for adding new queue item.

### May 24th changes:
1. Update namings of two column headers on queuing page.

### May 25th changes:
1. Update location of ajax calling of 'addQueueItem', only call when entered String/number is non-empty.
2. Add repository/service/impl methods for querying queueItems that is created on current date.
3. Update QueuingController and Thymeleaf fragment for displaying queried items created on current date on client side.
4. Add 'status' field to database visio design/init sql and entity class for QueueItem. To distinguish between 'waiting', 'preparing', 'completed' status.

### May 26th changes:
1. Update one of the user info to order@vidarflooring.com
2. Add two repository methods for querying queueItem with packingSlipNo and status.
3. Add service/impl methods for updating queueItem status and getting list of queueItems with different status created on current date.

### May 27th changes:
1. Add put mapping method for updating queue item status.
2. Add three lists of queue items with different status.
3. Update \<ul\> id's of preparing-list and completed-list.
4. Add thymeleaf rendering of three lists of queue items.
5. Force automated refresh of queuing page every 90 seconds.
6. Add ajax callings upon dragging/moving of queue items.
7. Update touch end event handlers.

### May 28th changes:
1. Update database initialization query, make sure 'packing_slip_no' in table 'test_queuing' is unique.
2. Add boolean method for QueueItemRepository to find out if packingSlipNo exists in database.
3. Add service/impl methods for queue items to judge if the item with packingSlipNo exists.
4. Update javascript code for queuing page, only add queueItem when it does not exist in database.

### May 29th changes:
1. Update findAllByStatusAndCreateTimeToday query by ordering by createTime descent.
2. Update addQueueItem controller method, skip adding item to database if packingSlipNo duplicated
3. Update html code for queue page, mostly for header to align with other pages.

### June 1st changes:
1. Add max-height property of .column.ul to enable the vertical direction scrolling.
2. Add is_back_order boolean field/attribute to table 'sales_orders'.

### June 2nd changes:
1. Add 'isBackOrder' field/property to entity class 'SalesOrder' with default value 'false'.

### June 3rd changes:
1. Add condition branch for adding new item without multipart image file.

### June 6th changes:
1. Update property file for testing. 

### June 7th changes:
1. Add sql files for ProductControllerTest.

### June 8th changes:
Note: table 'joint_types' is not used at all!
1. Update unit testing for product controller.

### June 9th changes:
1. Update application property file for unit testing, to avoid the conflict of schema between hibernate and flyway.
Note: in order to do product related testing, make sure V3__Add_products.sql add nothing to product table.
2. Update unit testing for products for the testing of number of products on page.

### June 10th changes:
1. Update the names of attribute searching checkbox fragments.
2. Reactivate the attribute searching navbar on products page.

### June 11th changes:
1. Update getProductsByFilterParams method to make width filtering work by remove quote from width input strings.

### June 12th changes:
1. Add test for product filtering by colours.

### June 15th changes:
1. Add test for product filtering by colours and widths.

### June 17th changes:
1. Update repository and service implementation methods for products, to accommodate for three filtering conditions.

### June 18th changes:
Note for android studio setup.
Update environment variable 'android_home' to correct path, otherwise emulator cannot find the corresponding sdk.

### June 19th changes:
1. Add VidarApp android app to github version control.
2. Add retrofit calling to springboot getmapping api, not tested yet.

### June 20th changes:
1. Add table 'Samples' to database.

### June 21st changes:
1. Add table 'samples' to database initialization.

### June 22nd changes:
1. Add entity class 'Sample' for table 'samples'.
2. Update schema initialization for table 'samples', keep using type 'int8' for id's.

### June 24th changes:
1. Add html fragments for users to orders samples.

### June 26th changes:
1. Add repository interface for 'Sample' class.

### June 28th changes:
1. Rename the controller method of adding product to cart.

### June 30th changes:
1. Add the controller method of adding sample to cart.

### July 1st changes:
1. Add sample service and implementation methods for sample related CRUD.

### July 2nd changes:
1. Add Pages/PathConstants constants for checkout page.

### July 3rd changes:
1. Add checkout-information web page template for shipping info filling.

Note: the page requires further formatting and polishing.