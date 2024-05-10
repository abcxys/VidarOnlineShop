-- create warehouse entries
INSERT INTO warehouses (id, city, address, description) VALUES (1, 'Toronto', 'Unit #2, 1775 Sismet Road, Mississauga, ON, L4W 1P9', null);
INSERT INTO warehouses (id, city, address, description) VALUES (2, 'Montreal', '160-6391 Westminster Hwy, Richmond, BC V7C 4V4', null);
INSERT INTO warehouses (id, city, address, description) VALUES (3, 'Vancouver', '9325, Trans-Canada Highway, St-Laurent, QC, H4S1V3' , null);
-- create driver entries
INSERT INTO drivers (id, name, cellphone, description) VALUES (1, 'Driver', 'N/A', null);
-- create packing_status entries
INSERT INTO packing_status (id, name, alias, description) VALUES (1, 'created', 'CR', null);
INSERT INTO packing_status (id, name, alias, description) VALUES (2, 'preparing', 'PP', null);
INSERT INTO packing_status (id, name, alias, description) VALUES (3, 'ready', 'RD', null);
INSERT INTO packing_status (id, name, alias, description) VALUES (4, 'loaded', 'LD', null);
-- create sales_order_status entries
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (1, 'created', 'CR');
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (2, 'picked up', 'PU');
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (3, 'completed', 'CP');
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (4, 'cancelled', 'CL');
-- create return_status entries
INSERT INTO return_status (id, name, alias, description) VALUES (1, 'created', 'CR', null);
INSERT INTO return_status (id, name, alias, description) VALUES (2, 'finalized', 'FN', null);

-- create sales_order entries
INSERT INTO sales_orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id, SO_number, PO_number, dealer_id, sales_rep_id, warehouse_id, status_id, release_ok)
    VALUES (1, 'Wall Street1', '3', '2021-04-07', 'Completed', 'Brampton Hardwood', 'Brampton Hardwood', '1234567890', 1234567890, 840, 2, '111', '111', 1, 1, 1, 1, true);

-- create sales_orders_products entries
INSERT INTO sales_orders_products (id, so_id, product_id, quantity_ordered, quantity_picked_up)
    VALUES (1, 1, 4, 80, 0);
INSERT INTO sales_orders_products (id, so_id, product_id, quantity_ordered, quantity_picked_up)
    VALUES (2, 1, 3, 20, 0);
--INSERT INTO salesOrders_products (sales_order_id, hardwoodfloors_id, quantity) VALUES (1, 1, 54);