INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id, store_id, SO_number, PO_number, invoice_number)
    VALUES (1, 'Wall Street1', '3', '2021-04-07', 'Completed', '61109', 'AA Floors', '1234567890', 1234567890, 840, 2, 1, 111, 111, 111);

INSERT INTO orders_hardwoodfloors (order_id, hardwoodfloors_id, quantity) VALUES (1, 1, 54);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id) VALUES (1, 99, 199, 1);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id) VALUES (2, 1188, 1188, 2);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id) VALUES (3, 658, 1188, 3);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id) VALUES (4, 56, 1188, 5);