insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Ducati',
   '750SS',
   1978,
   36000,
   3200.00,
   'PETROL',
   64,
   67,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'N', 750, 'SPORT');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Harley-Davidson',
   'XLH1000',
   1979,
   41000,
   3500.00,
   'PETROL',
   61,
   79,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'N', 1000, 'CRUISER');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Yamaha',
   'FJ1200',
   1986,
   47000,
   2900.00,
   'PETROL',
   95,
   93,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'Y', 1200, 'TOURING');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Suzuki',
   'GS550',
   1977,
   39000,
   2100.00,
   'PETROL',
   49,
   44,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 550, 'NAKED');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Honda',
   'CX500',
   1980,
   34000,
   2000.00,
   'PETROL',
   50,
   45,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 500, 'TOURING');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Kawasaki',
   'Z650',
   1979,
   42000,
   2300.00,
   'PETROL',
   64,
   59,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'N', 650, 'NAKED');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'BMW',
   'R80',
   1984,
   37000,
   2600.00,
   'PETROL',
   50,
   60,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'Y', 800, 'TOURING');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Moto Guzzi',
   'V50',
   1981,
   32000,
   1800.00,
   'PETROL',
   45,
   44,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 500, 'TOURING');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Suzuki',
   'GSX1100',
   1982,
   48000,
   2700.00,
   'PETROL',
   100,
   95,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'Y', 1100, 'SPORT');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Yamaha',
   'XS400',
   1978,
   33000,
   1600.00,
   'PETROL',
   36,
   34,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 400, 'NAKED');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Honda',
   'CB350',
   1973,
   29000,
   1400.00,
   'PETROL',
   34,
   32,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 350, 'NAKED');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'Kawasaki',
   'Z400',
   1980,
   31000,
   1500.00,
   'PETROL',
   37,
   35,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'Y', 'N', 400, 'NAKED');

insert into vehicle (make, model, year, mileage, price, engine, power, torque, type) values (
   'BMW',
   'R100RS',
   1981,
   45000,
   3200.00,
   'PETROL',
   70,
   80,
   'MOTORCYCLE'
);
insert into motorcycle (id, is_A2_compatible, has_ABS, engine_capacity, type) values (vehicle_id_seq.CURRVAL, 'N', 'Y', 1000, 'TOURING');

commit;