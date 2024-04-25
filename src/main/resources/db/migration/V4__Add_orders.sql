-- create warehouse entries
INSERT INTO warehouses (id, city, address, description) VALUES (1, 'Toronto', 'Unit #2, 1775 Sismet Road, Mississauga, ON, L4W 1P9', null);
INSERT INTO warehouses (id, city, address, description) VALUES (2, 'Montreal', '160-6391 Westminster Hwy, Richmond, BC V7C 4V4', null);
INSERT INTO warehouses (id, city, address, description) VALUES (3, 'Vancouver', '9325, Trans-Canada Highway, St-Laurent, QC, H4S1V3' , null);
-- create driver entries
INSERT INTO drivers (id, name, cellphone, description) VALUES (1, 'XiaoLi', '123123123', null);
-- create packing_status entries
INSERT INTO packing_status (id, name, alias, description) VALUES (1, 'preparing', 'PP', null);
INSERT INTO packing_status (id, name, alias, description) VALUES (2, 'ready', 'RD', null);
INSERT INTO packing_status (id, name, alias, description) VALUES (3, 'loaded', 'LD', null);
-- create sales_order_status entries
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (1, 'created', 'CR');
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (2, 'picked up', 'PU');
INSERT INTO sales_order_status (id, status_name, status_alias) VALUES (3, 'completed', 'CP');

-- create sales_order entries
INSERT INTO sales_orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id, SO_number, PO_number, dealer_id, sales_rep_id, warehouse_id, status_id, release_ok)
    VALUES (1, 'Wall Street1', '3', '2021-04-07', 'Completed', '61109', 'AA Floors', '1234567890', 1234567890, 840, 2, '111', '111', 1, 1, 1, 1, true);

--INSERT INTO salesOrders_products (sales_order_id, hardwoodfloors_id, quantity) VALUES (1, 1, 54);