-- password: admin
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
values(1, 'admin@gmail.com', 'Admin', 'Admin', null, null, null, null, null, true, '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.', null);

-- password: admin
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
values(2, 'test123@test.com', 'John', 'Doe', 'New York', 'Wall Street1', '1234567890', '1234567890', null, true, '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.', null);

-- password: admin
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
values(3, 'ivan123@test.com', 'Ivan', 'Ivanov', 'Moscow', 'Tverskaya street 1', '1234567890', '1234567890', null, true, '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.', null);

insert into user_role (user_id, roles) values (1, 'ADMIN');
insert into user_role (user_id, roles) values (2, 'USER');
insert into user_role (user_id, roles) values (3, 'USER');

insert into plank_sizes	(id, width_in_inch, length, thickness_in_inch, squarefoot_per_carton, description)
values	(1, '6', 'RL', '3/4', '19.96', '');
insert into plank_sizes	(id, width_in_inch, length, thickness_in_inch, squarefoot_per_carton, description)
values	(2, '7', 'RL', '3/4', '23.38', '');
insert into plank_sizes	(id, width_in_inch, length, thickness_in_inch, squarefoot_per_carton, description)
values	(3, '7-1/2', 'RL', '3/4', '25.93', '');
insert into plank_sizes	(id, width_in_inch, length, thickness_in_inch, squarefoot_per_carton, description)
values	(4, '8', 'RL', '3/4', '26.66', '');

insert into plank_colors (id, name, alias, description)
values (1, 'Natural', 'NUT', null);
insert into plank_colors (id, name, alias, description)
values (2, 'Naked Oak', 'NAK', null);
insert into plank_colors (id, name, alias, description)
values (3, 'Smoke Grey', 'SMG', null);

insert into wood_species (id, name, country, description)
values (1, 'American White Oak', null, null);
insert into wood_species (id, name, country, description)
values (2, 'American Black Walnut', null, null);

insert into plank_style (id, name, description)
values (1, 'regular', null);
insert into plank_style (id, name, description)
values (2, 'click', null);

insert into grades (id, name, alias, description)
values (1, 'Character', 'ABCD', null);
insert into grades (id, name, alias, description)
values (2, 'Select', 'ABC', null);
insert into grades (id, name, alias, description)
values (3, 'Select & Better', 'AB', null);

insert into joint_types (id, name, description)
values (1, 'Regular', null);
insert into joint_types (id, name, description)
values (2, 'Herringbone', null);