INSERT INTO sales_orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id, dealer_id, SO_number, PO_number, invoice_number)
    VALUES (1, 'Wall Street1', '3', '2021-04-07', 'Completed', '61109', 'AA Floors', '1234567890', 1234567890, 840, 2, 1, 111, 111, 111);

INSERT INTO sales_orders_hardwoodfloors (sales_order_id, hardwoodfloors_id, quantity) VALUES (1, 1, 54);