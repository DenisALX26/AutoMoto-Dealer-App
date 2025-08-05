create table customer (
    cnp varchar2(13) primary key,
    first_name varchar2(50) not null,
    last_name varchar2(50) not null,
    email varchar2(100) unique,
    phone varchar2(15)
);