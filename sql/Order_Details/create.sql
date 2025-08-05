create table order_details (
    order_id number(10) not null,
    vehicle_id number(10) not null,
    constraint order_details_pk primary key (order_id, vehicle_id),
    constraint order_details_fk1 foreign key (order_id) references order_table(id),
    constraint order_details_fk2 foreign key (vehicle_id) references vehicle(id)
);