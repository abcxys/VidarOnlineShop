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
insert into plank_colors (id, name, alias, description)
values (4, 'Drift Wood', 'DFW', null);
insert into plank_colors (id, name, alias, description)
values (5, 'Landmark', 'LMK', null);
insert into plank_colors (id, name, alias, description)
values (6, 'Fortino', 'FTN', null);
insert into plank_colors (id, name, alias, description)
values (7, 'Seashell', 'SSH', null);

insert into wood_species (id, name, country, description)
values (1, 'American White Oak', null, null);
insert into wood_species (id, name, country, description)
values (2, 'American Black Walnut', null, null);
insert into wood_species (id, name, country, description)
values (3, 'American Hickory', null, null);

insert into plank_types (id, name, description)
values (1, 'regular', null);
insert into plank_types (id, name, description)
values (2, 'Herringbone', null);

insert into grades (id, name, alias, description)
values (1, 'Character', 'ABCD', null);
insert into grades (id, name, alias, description)
values (2, 'Select', 'ABC', null);
insert into grades (id, name, alias, description)
values (3, 'Select & Better', 'AB', null);