create table PURCHASE_ORDER (
    id bigint auto_increment,
    PRODUCT_ID varchar(50),
    USER_ID int,
    AMOUNT int,
    STATUS varchar(50),
    primary key (id)
);