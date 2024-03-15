--liquibase formatted sql

--changeset alexey.balakin:1

create table if not exists category
(
    id      bigint primary key,
    name    varchar not null
);

insert into category (id, name) values (1, 'Category 1');
insert into category (id, name) values (2, 'Category 2');
insert into category (id, name) values (3, 'Category 3');
insert into category (id, name) values (4, 'Category 4');
insert into category (id, name) values (5, 'Category 5');

create sequence if not exists category_id_seq cycle minvalue 1000;

create table if not exists product
(
    id            bigint primary key,
    name          varchar not null,
    description   varchar,
    price         numeric(13, 2) not null,
    category_id   bigint references category(id)
);

insert into product (id, name, description, price, category_id) values (1, 'Product 1', 'Product 1 desc', 123.15, 1);
insert into product (id, name, description, price, category_id) values (2, 'Product 2', 'Product 2 desc', 1023, 1);
insert into product (id, name, description, price, category_id) values (3, 'Product 3', 'Product 3 desc', 10.11, 5);
insert into product (id, name, description, price, category_id) values (4, 'Product 4', 'Product 4 desc', 104567.01, 1);
insert into product (id, name, description, price, category_id) values (5, 'Product 5', 'Product 5 desc', 10127, 2);

create sequence if not exists product_id_seq cycle minvalue 1000;

--rollback drop table IF EXIST product;
--rollback drop table IF EXIST category;
--rollback drop sequence if exists product_id_seq;
--rollback drop sequence if exists category_id_seq;
