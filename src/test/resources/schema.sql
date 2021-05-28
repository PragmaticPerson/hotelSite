DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS people CASCADE;

CREATE TABLE rooms (
    id SERIAL,
    title CHARACTER VARYING(60) DEFAULT NULL,
    src_name CHARACTER VARYING(10) DEFAULT NULL,
    max_people INTEGER,
    price INTEGER,
    CONSTRAINT rooms_pk PRIMARY KEY (id)
);

CREATE TABLE people (
    id SERIAL,
    name CHARACTER VARYING(30) DEFAULT NULL,
    surname CHARACTER VARYING(30) DEFAULT NULL,
    phone CHARACTER VARYING(15) DEFAULT NULL,
    email CHARACTER VARYING(50) DEFAULT NULL,
    CONSTRAINT people_pk PRIMARY KEY (id)
);

CREATE TABLE orders (
    orders_id SERIAL,
    people_id INTEGER DEFAULT NULL,
    people_count INTEGER DEFAULT NULL,
    settling DATE,
    eviction DATE,
    CONSTRAINT orders_pk PRIMARY KEY (orders_id),
    CONSTRAINT people_fk FOREIGN KEY (people_id)
        REFERENCES people (id) ON DELETE SET DEFAULT
);

CREATE TABLE rooms_orders (
    rooms_id INTEGER NOT NULL,
    orders_id INTEGER NOT NULL,
    CONSTRAINT rooms_orders_pkey PRIMARY KEY (rooms_id, orders_id),
    CONSTRAINT rooms_ro_fk FOREIGN KEY (rooms_id)
        REFERENCES rooms (id) ON DELETE CASCADE,
    CONSTRAINT orders_ro_fk FOREIGN KEY (orders_id)
        REFERENCES orders (orders_id) ON DELETE CASCADE
);

INSERT INTO rooms (title, src_name, max_people, price) VALUES 
    ('Одноместный номер', 'single', 1,  2400),
    ('Двухместный номер', 'double', 2,  4500),
    ('Двухместный номер с двухэтажной кроватью', 'double-eco', 2, 4000),
    ('Трехместный номер', 'triple', 3, 6200);