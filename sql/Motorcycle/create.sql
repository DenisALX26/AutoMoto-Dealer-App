create table motorcycle (
    id number primary key,
    engine_capacity number(4) not null check ( engine_capacity > 0 ),
    has_ABS char(1) not null check ( has_ABS in ( 'Y', 'N' ) ),
    is_A2_compatible char(1) not null check ( is_A2_compatible in ( 'Y', 'N' ) ),
    type varchar2(20) not null check ( upper(type) in ( 'SPORT','CRUISER','TOURING','NAKED' ) ),
    constraint motorcycle_fk_vehicle foreign key (id) references vehicle(id)
    on DELETE CASCADE
);