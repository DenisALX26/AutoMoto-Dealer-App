create sequence vehicle_id_seq start with 1 increment by 1;

create table vehicle (
   id      number(10) primary key,
   make    varchar2(50) not null,
   model   varchar2(50) not null,
   year    number(4) not null,
   mileage number(10) default 0 not null check ( mileage >= 0 ),
   price   number(10,2) not null check ( price >= 0 ),
   engine  varchar2(10) not null check ( upper(engine) in ( 'PETROL',
                                                           'DIESEL',
                                                           'HYBRID',
                                                           'ELECTRIC' ) ),
   power   number(4) not null check ( power >= 0 ),
   torque  number(4) not null check ( torque >= 0 ),
   type    varchar2(11) not null check ( upper(type) in ( 'CAR',
                                                       'MOTORCYCLE' ) )
);

create or replace trigger trg_vehicle_autoincrement before
   insert on vehicle
   for each row
begin
   if :new.id is null then
      :new.id := vehicle_id_seq.nextval;
   end if;
end;
/

create or replace trigger trg_check_vehicle_year before
   insert or update on vehicle
   for each row
begin
   if :new.year < 1900
   or :new.year > to_number ( to_char(
      sysdate,
      'YYYY'
   ) ) then
      raise_application_error(
         -20001,
         'Anul trebuie să fie între 1900 și anul curent.'
      );
   end if;
end;
/