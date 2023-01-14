CREATE TABLE daangn_db.member
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    area       varchar(255) null,
    email      varchar(255) null,
    name       varchar(255) null,
    nickname   varchar(255) null,
    password   varchar(255) null,
    phone      varchar(255) null,
    role       varchar(255) null
);

CREATE TABLE daangn_db.product
(
    product_id bigint auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    area       varchar(255) null,
    category   varchar(255) null,
    content    varchar(255) null,
    name       varchar(255) null,
    price      bigint null,
    status     varchar(255) null,
    title      varchar(255) null
);

CREATE TABLE daangn_db.image
(
    image_id   bigint auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    file_name  varchar(255) null,
    product_id bigint null,
    constraint FKgpextbyee3uk9u6o2381m7ft1
        foreign key (product_id) references daangn_db.product (product_id)
);
