create sequence hardwoodfloor_id_seq start 109 increment 1;
create sequence users_id_seq start 4 increment 1;
create sequence order_item_seq start 12 increment 1;
create sequence orders_seq start 6 increment 1;

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
    primary key (id)
);
create table orders_perfumes
(
    order_id    int8 not null,
    perfumes_id int8 not null
);
create table receptionist
(
    id          int8 generated by default as identity,
    first_name  varchar(255) not null
);
create table hardwoodfloors
(
    id                     int8 not null,
    country                varchar(255) not null,
    description            varchar(255),
    filename               varchar(255),
    fragrance_base_notes   varchar(255) not null,
    fragrance_middle_notes varchar(255) not null,
    fragrance_top_notes    varchar(255) not null,
    perfume_gender         varchar(255) not null,
    perfume_title          varchar(255) not null,
    perfumer               varchar(255) not null,
    price                  int4 not null,
    type                   varchar(255) not null,
    volume                 varchar(255) not null,
    year                   int4 not null,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table users
(
    id                  int8    not null,
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
create table users_perfume_list
(
    user_id         int8 not null,
    perfume_list_id int8 not null
);
alter table if exists orders
    add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table if exists orders_perfumes
    add constraint FK9c3l5vucp0yrrwpc0v8vk8knt foreign key (perfumes_id) references hardwoodfloors;
alter table if exists orders_perfumes
    add constraint FK8jft4d30d5dgvauht7ssndwau foreign key (order_id) references orders;
alter table if exists user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
alter table if exists users_perfume_list
    add constraint FK9neiuundys2yde15o8tcybbvw foreign key (perfume_list_id) references hardwoodfloors;
alter table if exists users_perfume_list
    add constraint FKsnhp8qv0xdtp17dmdrhq201ym foreign key (user_id) references users;
