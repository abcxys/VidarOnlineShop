-- create inventory event type
INSERT INTO inventory_event_type (id, name, alias, description) VALUES (1, 'manual-add', 'MAN-ADD', null);
INSERT INTO inventory_event_type (id, name, alias, description) VALUES (2, 'manual-minus', 'MAN-MIN', null);
INSERT INTO inventory_event_type (id, name, alias, description) VALUES (3, 'manual-set', 'MAN-SET', null);
-- create warehouse locations
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (1, 1, 'A3', null);
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (2, 1, 'A4', null);
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (3, 1, 'A5', null);
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (4, 1, 'A6', null);
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (5, 1, 'E10', null);
INSERT INTO locations (id, warehouse_id, bay, description) VALUES (6, 1, 'F2', null);
-- create inventory entries
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (1, 99, 199, 1, 1);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (2, 1188, 1188, 2, 2);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (3, 658, 1188, 3, 3);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (4, 56, 1188, 5, 4);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (5, 200, 200, 1, 5);
INSERT INTO inventory (id, current_quantity, initial_quantity, floor_id, location_id) VALUES (6, 500, 500, 2, 6);