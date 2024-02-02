create table users (
    id bigint auto_increment,
    name varchar(50),
    balance int,
    primary key (id)
);

create table user_transaction(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    foreign key (user_id) references users (id) on delete cascade
);

insert into users
    (name, balance)
    values
    ('Raaj', 10000),
    ('Aaaj', 12000),
    ('Kaaj', 8000),
    ('Taaj', 20000);