create table DICT_GOODS
(
    id         long auto_increment primary key,
    name       varchar2 not null unique,
    goods_type varchar2 not null
);

create table DICT_CITY_NAME
(
    id   long auto_increment primary key,
    name varchar2 not null unique
);

create table CITY_INFO
(
    id   long auto_increment primary key,
    name varchar2 not null unique
);

create table VILLAGE_INFO
(
    id         long auto_increment primary key,
    city_name  varchar2 not null,
    goods_name varchar2 not null
);

create table WORKSHOP_INFO
(
    id        long auto_increment primary key,
    city_name varchar2 not null,
    name      varchar2 not null
);

create table SURPLUS_GOODS_INFO
(
    id        long auto_increment primary key,
    city_name varchar2 not null,
    name      varchar2 not null,
    important boolean,
    price     varchar2
);

create table DEFICIT_GOODS_INFO
(
    id        long auto_increment primary key,
    city_name varchar2 not null,
    name      varchar2 not null,
    important boolean,
    price     varchar2
);