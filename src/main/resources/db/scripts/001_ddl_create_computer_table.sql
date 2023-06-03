CREATE TABLE IF NOT EXISTS computer
(
    id SERIAL PRIMARY KEY ,
    serial_number VARCHAR UNIQUE NOT NULL ,
    manufacturer VARCHAR NOT NULL ,
    price INT NOT NULL ,
    quantity INT NOT NULL,
    type VARCHAR NOT NULL

);

comment on table computer is 'Таблица компьютеров';
comment on column computer.id is 'Идентификатор';
comment on column computer.serial_number is 'Номер серии';
comment on column computer.manufacturer is 'Производитель';
comment on column computer.price is 'Цена';
comment on column computer.quantity is 'Кол-во единиц на складе';
comment on column computer.type is 'Тип компьютера';