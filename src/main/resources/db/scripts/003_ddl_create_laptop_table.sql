CREATE TABLE IF NOT EXISTS laptop
(
    id            SERIAL PRIMARY KEY,
    serial_number VARCHAR UNIQUE NOT NULL,
    manufacturer  VARCHAR        NOT NULL,
    price         INT            NOT NULL,
    quantity      INT            NOT NULL,
    size          INT            NOT NULL

);

comment on table laptop is 'Таблица ноутбуков';
comment on column laptop.id is 'Идентификатор';
comment on column laptop.serial_number is 'Номер серии';
comment on column laptop.manufacturer is 'Производитель';
comment on column laptop.price is 'Цена';
comment on column laptop.quantity is 'Кол-во единиц на складе';
comment on column laptop.size is 'Размер ноутбука';