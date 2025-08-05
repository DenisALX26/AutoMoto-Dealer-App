create sequence employee_id_seq start with 1 increment by 1;

create table employee (
    id number primary key,
    first_name varchar2(50) not null,
    last_name varchar2(50) not null,
    hire_date date not null,
    commission number(5,2) default 0
);

create or replace trigger employee_id_trigger
before insert on employee
for each row
begin
    if :new.id is null then
        :new.id := employee_id_seq.NEXTVAL;
    end if;
end;