DROP TABLE IF EXISTS room_reservation CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS client_roles CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS s_role CASCADE;
CREATE TABLE room
(
   id SERIAL PRIMARY KEY,
   title CHARACTER VARYING (60) DEFAULT NULL,
   source CHARACTER VARYING (10) DEFAULT NULL,
   max_people INTEGER,
   price INTEGER
);
CREATE TABLE client
(
   id SERIAL PRIMARY KEY,
   password CHARACTER VARYING (60) NOT NULL,
   name CHARACTER VARYING (30) DEFAULT NULL,
   surname CHARACTER VARYING (30) DEFAULT NULL,
   phone CHARACTER VARYING (15) DEFAULT NULL,
   email CHARACTER VARYING (50) DEFAULT NULL
);
CREATE TABLE s_role
(
   id SERIAL PRIMARY KEY,
   name CHARACTER VARYING(10) NOT NULL
);
CREATE TABLE client_roles
(
   client_id INTEGER,
   role_id INTEGER,
   PRIMARY KEY (client_id, role_id),
   FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES s_role (id) ON DELETE CASCADE
);
CREATE TABLE reservation
(
   id SERIAL PRIMARY KEY,
   client_id INTEGER DEFAULT NULL,
   people_count INTEGER DEFAULT NULL,
   settling DATE,
   eviction DATE,
   CONSTRAINT client_fk FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE SET DEFAULT
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
INSERT INTO room
(
   title,
   source,
   max_people,
   price
)
VALUES
(
   'Одноместный номер',
   'single',
   1,
   2400
),

(
   'Двухместный номер',
   'double',
   2,
   4500
),

(
   'Двухместный номер с двухэтажной кроватью',
   'double-eco',
   2,
   4000
),

(
   'Трехместный номер',
   'triple',
   3,
   6200
);

INSERT INTO s_role
( name ) VALUES
('ADMIN'), ('USER');