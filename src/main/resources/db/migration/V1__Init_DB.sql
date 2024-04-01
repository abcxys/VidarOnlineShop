create sequence hardwoodfloor_id_seq start 1 increment 1;
create sequence plank_size_id_seq start 5 increment 1;
create sequence plank_color_id_seq start 8 increment 1;
create sequence plank_type_id_seq start 3 increment 1;
create sequence wood_species_id_seq start 4 increment 1;
create sequence grade_id_seq start 4 increment 1;
create sequence users_id_seq start 4 increment 1;
create sequence order_item_seq start 12 increment 1;
create sequence orders_seq start 6 increment 1;

create table users
(
    id                  int8 generated by default as identity,
    activation_code     varchar(255),
    active              boolean not null,
    address             varchar(255),
    city                varchar(255),
    email               varchar(255) not null,
    first_name          varchar(255) not null,
    last_name           varchar(255),
    password            varchar(255) not null,
    password_reset_code varchar(255),
    phone_number        varchar(255),
    post_index          varchar(255),
    primary key (id)
);
create table orders
(
    id           int8 generated by default as identity,
    address      varchar(255) not null,
    city         varchar(255) not null,
    date         timestamp default current_timestamp,
    email        varchar(255) not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    phone_number varchar(255) not null,
    post_index   int4 not null,
    total_price  float8 not null,
    user_id      int8,
    store_id    int8 not null,
    SO_number   int8 not null,
    PO_number   int8 not null,
    invoice_number int8 not null,
    primary key (id)
);
create table store
(
    id           int8 generated by default as identity,
    name        varchar(255) not null,
    address     varchar(255) not null,
    phone_number    varchar(255) not null,
    primary key (id)
);
create table orders_hardwoodfloors
(
    id          int8 generated by default as identity,
    order_id    int8 not null,
    hardwoodfloors_id int8 not null,
    quantity    float not null
);
create table receptionists
(
    id          int8 generated by default as identity,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    alias       varchar(255) not null,
    description varchar(255),
    PRIMARY KEY(id)
);
create table plank_sizes
(
    id          int8 generated by default as identity,
    width_in_inch   varchar(255) not null,
    length      varchar(255) not null,
    thickness_in_inch   varchar(255) not null,
    squarefoot_per_carton   float not null,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255),
    PRIMARY KEY(id),
    UNIQUE(width_in_inch, length, thickness_in_inch, squarefoot_per_carton)
);
create table wood_species
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    country     varchar(255) default 'American',
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255),
    PRIMARY KEY(id),
    UNIQUE(name)
);
create table plank_colors
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    alias       varchar(255) not null,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255),
    UNIQUE(name),
    PRIMARY KEY(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id)
);
create table plank_types
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    alias       varchar(255),
    description varchar(255),
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    UNIQUE(name),
    PRIMARY KEY(id)
);
create table grades
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    alias       varchar(255) not null,
    description varchar(255),
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    PRIMARY KEY(id)
);
create table joint_types
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    description varchar(255),
    PRIMARY KEY(id)
);
create table finishes
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    description varchar(255),
    PRIMARY KEY(id)
);
create table hardwoodfloors
(
    id                     int8 not null,
    country                varchar(255) not null,
    description            varchar(255),
    filename               varchar(255),
    price                  float4 not null,
    year                   int4 not null,
    plank_size_id           int8 not null,
    plank_color_id          int8 not null,
    wood_species_id         int8 not null,
    plank_type_id int8 not null,
    grade_id int8 not null,
    batch_id                varchar(255),
    cartons_per_skid        int4,
    wear_layer_thickness        float4 default 2.0,
    carton_weight           float4,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    PRIMARY KEY(id),
    CONSTRAINT fk_plank_size_id
        FOREIGN KEY(plank_size_id)
            REFERENCES plank_sizes(id),
    CONSTRAINT fk_plank_color_id
        FOREIGN KEY(plank_color_id)
            REFERENCES plank_colors(id),
    CONSTRAINT fk_wood_species_id
        FOREIGN KEY(wood_species_id)
            REFERENCES wood_species(id),
    CONSTRAINT fk_plank_type_id
        FOREIGN KEY(plank_type_id)
            REFERENCES plank_types(id),
    CONSTRAINT fk_grade_id
        FOREIGN KEY(grade_id)
            REFERENCES grades(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id)
);
create table inventory
(
    id          int8 generated by default as identity,
    create_time  timestamp default null,
    update_time timestamp default null,
    current_quantity float(1) not null,
    initial_quantity float(1) not null,
    description varchar(255) default null,
    floor_id    int8 not null,
    PRIMARY KEY(id),
    CONSTRAINT fk_floor_id
        FOREIGN KEY(floor_id)
            REFERENCES hardwoodfloors(id)
);
create table inventory_event_type
(
    id          int8 generated by default as identity,
    name        varchar(255),
    alias       varchar(255),
    description varchar(255),
    PRIMARY KEY(id)
);
create table inventory_event
(
    id          int8 generated by default as identity,
    inventory_id    int8 not null,
    inventory_event_type_id int8 not null,
    quantity    float(1) not null,
    create_time  timestamp,
    update_time timestamp,
    description varchar(255),
    CONSTRAINT fk_inventory_id
        FOREIGN KEY(inventory_id)
            REFERENCES inventory(id),
    CONSTRAINT fk_inventory_event_type_id
        FOREIGN KEY(inventory_event_type_id)
            REFERENCES inventory_event_type(id),
    PRIMARY KEY(id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table users_perfume_list
(
    user_id         int8 not null,
    perfume_list_id int8 not null
);
alter table if exists orders
    add constraint fk_store_id foreign key (store_id) references store;
alter table if exists orders
    add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table if exists orders_hardwoodfloors
    add constraint FK9c3l5vucp0yrrwpc0v8vk8knt foreign key (hardwoodfloors_id) references hardwoodfloors;
alter table if exists orders_hardwoodfloors
    add constraint FK8jft4d30d5dgvauht7ssndwau foreign key (order_id) references orders;
alter table if exists user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
alter table if exists users_perfume_list
    add constraint FK9neiuundys2yde15o8tcybbvw foreign key (perfume_list_id) references hardwoodfloors;
alter table if exists users_perfume_list
    add constraint FKsnhp8qv0xdtp17dmdrhq201ym foreign key (user_id) references users;
