create table car (
    id number primary key,
    drive_type varchar2(20) not null,
    color VARCHAR2(20) not null,
    number_of_doors number(2) not null,
    constraint car_fk_vehicle foreign key (id) references vehicle(id)
    on DELETE CASCADE
);