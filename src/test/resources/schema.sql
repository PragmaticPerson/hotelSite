DROP TABLE IF EXISTS room_reservation CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS room_type CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS s_user CASCADE;
DROP TABLE IF EXISTS s_role CASCADE;
DROP TABLE IF EXISTS reset_token CASCADE;
CREATE TABLE room_type
(
   id SERIAL PRIMARY KEY,
   title CHARACTER VARYING (60) DEFAULT NULL,
   source CHARACTER VARYING (20) DEFAULT NULL,
   max_people INTEGER,
   price INTEGER,
   image_count INTEGER,
   panorama_url_id CHARACTER VARYING (32) DEFAULT NULL,
   description CHARACTER VARYING (300)
);
CREATE TABLE room
(
   id SERIAL PRIMARY KEY,
   name CHARACTER VARYING (10) DEFAULT NULL,
   type_id INTEGER,
   FOREIGN KEY (type_id) REFERENCES room_type (id) ON DELETE CASCADE
);
CREATE TABLE s_user
(
   id SERIAL PRIMARY KEY,
   password CHARACTER VARYING (60) NOT NULL,
   name CHARACTER VARYING (30) DEFAULT NULL,
   surname CHARACTER VARYING (30) DEFAULT NULL,
   phone CHARACTER VARYING (15) DEFAULT NULL,
   email CHARACTER VARYING (50) NOT NULL UNIQUE
);
CREATE TABLE reset_token
(
   id SERIAL PRIMARY KEY,
   token CHARACTER VARYING (36) NOT NULL,
   user_id INTEGER,
   expiry_date TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES s_user (id) ON DELETE CASCADE
);
CREATE TABLE s_role
(
   id SERIAL PRIMARY KEY,
   name CHARACTER VARYING (10) NOT NULL
);
CREATE TABLE user_roles
(
   user_id INTEGER,
   role_id INTEGER,
   PRIMARY KEY
   (
      user_id,
      role_id
   ),
   FOREIGN KEY (user_id) REFERENCES s_user (id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES s_role (id) ON DELETE CASCADE
);
CREATE TABLE reservation
(
   id SERIAL PRIMARY KEY,
   user_id INTEGER DEFAULT NULL,
   adult_count INTEGER DEFAULT NULL,
   child_count INTEGER DEFAULT NULL,
   settling DATE,
   eviction DATE,
   total_price INTEGER,
   CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES s_user (id) ON DELETE SET DEFAULT
);
CREATE TABLE room_reservation
(
   room_id INTEGER NOT NULL,
   reservation_id INTEGER NOT NULL,
   CONSTRAINT room_reservation_pkey PRIMARY KEY
   (
      room_id,
      reservation_id
   ),
   CONSTRAINT room_rr_fk FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE,
   CONSTRAINT reservation_rr_fk FOREIGN KEY (reservation_id) REFERENCES reservation (id) ON DELETE CASCADE
);
INSERT INTO room_type
(
   title,
   source,
   max_people,
   price,
   image_count,
   panorama_url_id,
   description
)
VALUES
(
   'Одноместный люкс',
   '1-1L',
   1,
   5000,
   3,
   'fa691521095745c99ca9822c1ee19615',
   'Номер повышенного комфорта, который идеально подходит для одного гостя. Просторный и элегантный интерьер, мягкий ковер и удобная кровать создают атмосферу роскоши и уюта.'
),

(
   'Одноместный',
   '1-2',
   1,
   4000,
   2,
   '6bc2a53b79ff42f5828fa2dbd0943c5e',
   'Уютный и комфортабельный номер, который идеально подходит для одного гостя. Включает в себя все необходимые удобства и оборудование для комфортного проживания.'
),

(
   'Одноместный',
   '1-3',
   1,
   4000,
   2,
   '4637bd88996e4ad58a3b47f06178d6f5',
   'Номер, в котором каждая деталь продумана, чтобы обеспечить максимальный комфорт и удобство гостю. Мягкая кровать, просторный шкаф, телевизор и множество других удобств гарантируют приятный отдых.'
),

(
   'Двухместный люкс',
   '2-1L',
   2,
   9000,
   3,
   '35f59da1aaf3439fbaab7cb02081eb54',
   'Номер повышенного комфорта, который идеально подходит для пары. Просторный и элегантный интерьер, мягкий ковер и удобная кровать создают атмосферу роскоши и уюта.'
),

(
   'Двухместный с совместным размещением',
   '2-2',
   2,
   8000,
   3,
   '84d8e040d180463ea416dd3c20389716',
   'Уютный и комфортабельный номер, который идеально подходит для пары. Включает в себя все необходимые удобства и оборудование для комфортного проживания.'
),

(
   'Двухместный с раздельным размещением',
   '2-3',
   2,
   7500,
   3,
   '9555811ce134471eaebefcf0bf222323',
   'Номер, в котором каждая деталь продумана, чтобы обеспечить максимальный комфорт и удобство гостям. Мягкая кровать, просторный шкаф, телевизор и множество других удобств гарантируют приятный отдых вдвоем.'
);
INSERT INTO room
(
   name,
   type_id
)
VALUES
(
   '2-101',
   1
),

(
   '2-102',
   1
),

(
   '2-103',
   2
),

(
   '2-104',
   2
),

(
   '2-105',
   3
),

(
   '2-106',
   3
),

(
   '2-107',
   4
),

(
   '2-108',
   4
),

(
   '2-109',
   5
),

(
   '2-110',
   5
),

(
   '2-111',
   6
),

(
   '2-112',
   6
);
INSERT INTO s_role (name) VALUES ('ROLE_ADMIN'),
('ROLE_USER');