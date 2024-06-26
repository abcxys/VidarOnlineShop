create sequence hardwoodfloor_id_seq start 10 increment 1;
create sequence plank_size_id_seq start 5 increment 1;
create sequence plank_color_id_seq start 8 increment 1;
create sequence plank_type_id_seq start 3 increment 1;
create sequence wood_species_id_seq start 4 increment 1;
create sequence grade_id_seq start 4 increment 1;
create sequence users_id_seq start 4 increment 1;
create sequence order_item_seq start 12 increment 1;
create sequence orders_seq start 6 increment 1;
create sequence location_id_seq start 7 increment 1;

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
create table sales_reps
(
    id      int8 generated by default as identity,
    first_name  varchar(255),
    last_name   varchar(255) not null,
    alias       varchar(255) not null,
    description varchar(255),
    PRIMARY KEY (id)
);
create table drivers
(
    id      int8 generated by default as identity,
    name    varchar(255) not null,
    cellphone   varchar(255),
    description varchar(255),
    PRIMARY KEY (id),
    CONSTRAINT chk_info CHECK (name is not null or cellphone is not null)
);
create table dealer_types
(
    id          int8 generated by default as identity,
    type_name   varchar(255),
    type_alias  varchar(255),
    description varchar(255),
    PRIMARY KEY (id)
);
create table packing_status
(
    id          int8 generated by default as identity,
    name   varchar(255) not null,
    alias  varchar(255),
    description varchar(255),
    PRIMARY KEY (id)
);
create table return_status
(  
    id          int8 generated by default as identity,
    name    varchar(255) not null,
    alias   varchar(255),
    description varchar(255) default null,
    PRIMARY KEY (id)
);
create table dealers
(
    id           int8 generated by default as identity,
    type_id     int8 not null,
    company_name        varchar(255) not null,
    full_name   varchar(255) not null,
    address     varchar(255) not null,
    phone_number    varchar(255) not null,
    main_email1 varchar(255) not null,
    main_email2 varchar(255),
    cc_email    varchar(255),
    active      boolean not null,
    description varchar(255) default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_type_id
        FOREIGN KEY(type_id)
            REFERENCES dealer_types(id)
);
create table shipping_methods
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    alias       varchar(255),
    description varchar(255) default null,
    PRIMARY KEY (id)
);
create table packing_slips
(
    id          int8 generated by default as identity,
    dealer_id   int8 not null,
    packing_status_id   int8 not null,
    driver_id   int8 not null default 1,
    shipping_method_id  int8 not null default 1,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255) default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_driver_id
        FOREIGN KEY (driver_id)
            REFERENCES drivers(id),
    CONSTRAINT fk_dealer_id
        FOREIGN KEY (dealer_id)
            REFERENCES dealers(id),
    CONSTRAINT fk_packing_status_id
        FOREIGN KEY (packing_status_id)
            REFERENCES packing_status(id),
    CONSTRAINT fk_shipping_method_id
        FOREIGN KEY (shipping_method_id)
            REFERENCES shipping_methods(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id)
);
create table return_slips
(
    id          int8 generated by default as identity,
    packing_slip_id     int8 not null,
    return_status_id    int8 not null,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255) default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_packing_slip_id
        FOREIGN KEY(packing_slip_id)
            REFERENCES packing_slips(id),
    CONSTRAINT fk_return_status_id
        FOREIGN KEY(return_status_id)
            REFERENCES return_status(id) 
);
create table receptionists
(
    id          int8 generated by default as identity,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    alias       varchar(255) not null,
    description varchar(255),
    PRIMARY KEY (id)
);
create table plank_sizes
(
    id          int8 generated by default as identity,
    width_in_inch   varchar(255) not null,
    length      varchar(255) not null,
    thickness_in_inch   varchar(255) not null,
    squarefoot_per_carton   decimal(10,2) not null,
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
    country                varchar(255),
    description            varchar(255),
    filename               varchar(255),
    price                  decimal(10,2) not null,
    year                   int4,
    plank_size_id           int8 not null,
    plank_color_id          int8 not null,
    wood_species_id         int8 not null,
    plank_type_id int8 not null,
    grade_id int8 not null,
    batch_id                varchar(255),
    cartons_per_skid        int4,
    wear_layer_thickness        float4 default 2.0,
    carton_weight           float4 default 60.0,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    finish                  varchar(255) default null,
    active                  boolean not null default true,
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
create table warehouses
(
    id          int8 generated by default as identity,
    city    varchar(255) not null,
    address varchar(255),
    description varchar(255),
    PRIMARY KEY(id)
);
create table sales_order_status
(
    id          int8 generated by default as identity,
    status_name varchar(255) not null,
    status_alias varchar(255) not null,
    description varchar(255) default null,
    PRIMARY KEY(id)
);
create table sales_orders
(
    id           int8 generated by default as identity,
    address      varchar(255) not null,
    city         varchar(255) not null,
    date         timestamp default current_timestamp,
    date_wanted  timestamp default current_timestamp,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    phone_number varchar(255),
    post_index   int4,
    total_price  float8,
    user_id      int8,
    SO_number   varchar(255) not null,
    PO_number   varchar(255) not null,
    dealer_id   int8 not null,
    sales_rep_id    int8 not null,
    warehouse_id    int8 not null,
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    status_id   int8 not null,
    release_ok  boolean not null default true,
    is_back_order boolean not null default false,
    PRIMARY KEY (id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_dealer_id
        FOREIGN KEY(dealer_id)
            REFERENCES dealers(id),
    CONSTRAINT fk_sales_rep_id
        FOREIGN KEY(sales_rep_id)
            REFERENCES sales_reps(id),
    CONSTRAINT fk_warehouse_id
        FOREIGN KEY(warehouse_id)
            REFERENCES warehouses(id),
    CONSTRAINT fk_status_id
        FOREIGN KEY(status_id)
            REFERENCES sales_order_status(id)
);
create table locations
(
    id          int8 generated by default as identity,
    warehouse_id    int8 not null,
    bay         varchar(255) not null,
    description varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT fk_warehouse_id
        FOREIGN KEY(warehouse_id)
            REFERENCES warehouses(id),
    UNIQUE(warehouse_id, bay)
);
create table inventory
(
    id          int8 generated by default as identity,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id  int8,
    update_user_id  int8,
    current_quantity decimal(5, 1) not null,
    initial_quantity decimal(5, 1) not null,
    description varchar(255) default null,
    floor_id    int8 not null,
    location_id int8 not null,
    PRIMARY KEY(id),
    CONSTRAINT fk_floor_id
        FOREIGN KEY(floor_id)
            REFERENCES hardwoodfloors(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_location_id
        FOREIGN KEY(location_id)
            REFERENCES locations(id)
);
create table factory_inventory
(
    id              int8 generated by default as identity,
    create_time     timestamp default null,
    update_time     timestamp default null,
    create_user_id  int8,
    update_user_id  int8,
    current_quantity decimal(5, 1) not null,
    initial_quantity decimal(5, 1) not null,
    description varchar(255) default null,
    floor_id        int8 not null,
    location_id     int8 default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_floor_id
        FOREIGN KEY(floor_id)
            REFERENCES hardwoodfloors(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_location_id
        FOREIGN KEY(location_id)
            REFERENCES locations(id)
);
create table inventory_event_type
(
    id          int8 generated by default as identity,
    name        varchar(255) not null,
    alias       varchar(255) not null,
    description varchar(255),
    PRIMARY KEY(id)
);
create table container_status
(
    id          int8 generated by default as identity,
    status_name varchar(255) not null,
    status_alias    varchar(255) not null,
    description     varchar(255),
    PRIMARY KEY(id)
);
create table container
(
    id          int8 generated by default as identity,
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    target_warehouse_id int8 not null,
    container_status_id int8 not null,
    container_number    varchar(255) not null,
    billoflanding_number    varchar(255) not null,
    shipping_company    varchar(255) not null,
    freight_forwarder   varchar(255) not null,
    estimated_arrival_date  timestamp default null,
    arrival_date    timestamp default null,
    port_name       varchar(255),
    port_date       timestamp default null,
    on_rail_date    timestamp default null,
    description     varchar(255),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_container_status_id
        FOREIGN KEY(container_status_id)
            REFERENCES container_status(id),
    CONSTRAINT fk_target_warehouse_id
        FOREIGN KEY(target_warehouse_id)
            REFERENCES warehouses(id),
    PRIMARY KEY(id)
);
create table container_floors
(
    id          int8 generated by default as identity,
    container_id    int8 not null,
    floor_id    int8 not null,
    skid        int4 not null,
    quantity    decimal(5, 1) not null,
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    description varchar(255),
    CONSTRAINT fk_floor_id
        FOREIGN KEY(floor_id)
            REFERENCES hardwoodfloors(id),
    CONSTRAINT fk_container_id
        FOREIGN KEY(container_id)
            REFERENCES container(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    PRIMARY KEY(id)
);
create table inventory_event
(
    id          int8 generated by default as identity,
    inventory_id    int8 not null,
    inventory_event_type_id int8 not null,
    location_id     int8 not null,
    quantity    decimal(5, 1) not null,
    create_time  timestamp,
    create_user_id  int8,
    description varchar(255),
    CONSTRAINT fk_inventory_id
        FOREIGN KEY(inventory_id)
            REFERENCES inventory(id),
    CONSTRAINT fk_inventory_event_type_id
        FOREIGN KEY(inventory_event_type_id)
            REFERENCES inventory_event_type(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_location_id
        FOREIGN KEY(location_id)
            REFERENCES locations(id),
    PRIMARY KEY(id)
);
create table factory_inventory_event
(
    id          int8 generated by default as identity,
    factory_inventory_id        int8 not null,
    inventory_event_type_id     int8 not null,
    location_id                 int8 default null,
    quantity                    decimal(5, 1) not null,
    create_time                 timestamp,
    create_user_id              int8,
    description                 varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT fk_factory_inventory_id
        FOREIGN KEY(factory_inventory_id)
            REFERENCES factory_inventory(id),
    CONSTRAINT fk_inventory_event_type_id
        FOREIGN KEY(inventory_event_type_id)
            REFERENCES inventory_event_type(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_location_id
        FOREIGN KEY(location_id)
            REFERENCES locations(id)
);
create table sales_orders_products
(
    id          int8 generated by default as identity,
    so_id    int8 not null,
    product_id int8 not null,
    quantity_ordered    decimal(5,1) not null,
    quantity_picked_up   decimal(5,1),
    active boolean default true,
    create_time  timestamp default null,
    update_time timestamp default null,
    create_user_id int8,
    update_user_id int8,
    description varchar(255) default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_product_id
        FOREIGN KEY(product_id)
            REFERENCES hardwoodfloors(id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_so_id
        FOREIGN KEY(so_id)
            REFERENCES sales_orders(id)
);
create table sales_orders_packing
(
    id          int8 generated by default as identity,
    so_product_id       int8 not null,
    packing_slip_id     int8 not null,
    quantity    decimal(5,1),
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    description varchar(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_so_product_id
        FOREIGN KEY(so_product_id)
            REFERENCES sales_orders_products(id),
    CONSTRAINT fk_packing_slip_id
        FOREIGN KEY(packing_slip_id)
            REFERENCES packing_slips(id)
);
create table return_products
(
    id          int8 generated by default as identity,
    return_slip_id       int8 not null,
    product_id     int8 not null,
    quantity    decimal(5,1),
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    description varchar(255) default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_return_slip_id
        FOREIGN KEY(return_slip_id)
            REFERENCES return_slips(id),
    CONSTRAINT fk_product_id
        FOREIGN KEY(product_id)
            REFERENCES hardwoodfloors(id)
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
create table test_queuing
(
    id                  int8 generated by default as identity,
    packing_slip_no     varchar(255) not null,
    status              int4 not null,
    create_time timestamp,
    update_time timestamp,
    create_user_id  int8,
    update_user_id  int8,
    prepare_start   timestamp,
    PRIMARY KEY (id),
    UNIQUE(packing_slip_no),
    CONSTRAINT fk_create_user_id
        FOREIGN KEY(create_user_id)
            REFERENCES users(id),
    CONSTRAINT fk_update_user_id
        FOREIGN KEY(update_user_id)
            REFERENCES users(id)
);
create table samples
(
    id                  int8 generated by default as identity PRIMARY KEY,
    product_id          int8 NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES hardwoodfloors(id)
);
alter table if exists sales_orders
    add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table if exists user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
alter table if exists users_perfume_list
    add constraint FK9neiuundys2yde15o8tcybbvw foreign key (perfume_list_id) references hardwoodfloors;
alter table if exists users_perfume_list
    add constraint FKsnhp8qv0xdtp17dmdrhq201ym foreign key (user_id) references users;
