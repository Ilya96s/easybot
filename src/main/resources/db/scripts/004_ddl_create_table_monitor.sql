CREATE TABLE IF NOT EXISTS monitor
(
    id SERIAL PRIMARY KEY ,
    serial_number VARCHAR UNIQUE NOT NULL ,
    manufacturer VARCHAR NOT NULL ,
    price INT NOT NULL ,
    quantity INT NOT NULL ,
    diagonal INT NOT NULL

);

comment on table monitor is 'Таблица мониторов';
comment on column monitor.id is 'Идентификатор';
comment on column monitor.serial_number is 'Номер серии';
comment on column monitor.manufacturer is 'Производитель';
comment on column monitor.price is 'Цена';
comment on column monitor.quantity is 'Кол-во единиц на складе';
comment on column monitor.diagonal is 'Диагональ';