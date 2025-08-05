create sequence order_id_seq start with 1 increment by 1;

create table order_table (
    id number primary key,
    employee_id number not null,
    customer_cnp VARCHAR2(13) not null,
    order_date date not null,
    status varchar2(20) not null check (upper(status) in ('PENDING', 'SHIPPED', 'DELIVERED')),
    total_amount number not null,
    constraint fk_employee foreign key (employee_id) references employee(id),
    constraint fk_customer foreign key (customer_cnp) references customer(cnp)
);

create trigger order_trigger_autoincrement
before insert on order_table
for each row
begin
    if :new.id is null then
        :new.id := order_id_seq.nextval;
    end if;
end;