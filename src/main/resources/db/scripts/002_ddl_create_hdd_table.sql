CREATE TABLE IF NOT EXISTS hdd
(
    id            SERIAL PRIMARY KEY,
    serial_number VARCHAR UNIQUE NOT NULL,
    manufacturer  VARCHAR        NOT NULL,
    price         INT            NOT NULL,
    quantity      INT            NOT NULL,
    capacity      BIGINT         NOT NULL

);

comment on table hdd is 'Таблица жестких дисков';
comment on column hdd.id is 'Идентификатор';
comment on column hdd.serial_number is 'Номер серии';
comment on column hdd.manufacturer is 'Производитель';
comment on column hdd.price is 'Цена';
comment on column hdd.quantity is 'Кол-во единиц на складе';
comment on column hdd.capacity is 'Объем жесткого диска';