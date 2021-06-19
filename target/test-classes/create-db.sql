DROP SCHEMA IF EXISTS car_rental;
CREATE SCHEMA IF NOT EXISTS car_rental;
USE car_rental;

CREATE TABLE IF NOT EXISTS role
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login    VARCHAR(30)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(30),
    role_id  BIGINT       NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS customer
(
    id           BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(30) NOT NULL,
    phone_number VARCHAR(15),
    passport     VARCHAR(100),
    user_id      BIGINT      NOT NULL,
    balance      DOUBLE,
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS car_status
(
    id     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS car_model
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_make   VARCHAR(45) NOT NULL,
    name       VARCHAR(45) NOT NULL,
    year       INT         NOT NULL,
    body_style VARCHAR(45),
    fuel       VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS car
(
    id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_model_id  BIGINT NOT NULL,
    car_status_id BIGINT,
    total_mileage INT,
    FOREIGN KEY (car_model_id) REFERENCES car_model (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (car_status_id) REFERENCES car_status (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS order_status
(
    id     BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS rental_order
(
    id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_id          BIGINT NOT NULL,
    customer_id     BIGINT NOT NULL,
    order_status_id BIGINT NOT NULL,
    start_time      DATE   NOT NULL,
    end_time        DATE   NOT NULL,
    price           DOUBLE,
    info            VARCHAR(200),
    FOREIGN KEY (car_id) REFERENCES car (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (order_status_id) REFERENCES order_status (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO user (login, password, email, role_id)
VALUES ('admin', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 'admin@mail.com', 1);
INSERT INTO user (login, password, email, role_id)
VALUES ('testuser1', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 'vad@mail.com', 2);
INSERT INTO user (login, password, email, role_id)
VALUES ('testuser2', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', NULL, 2);

INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer1', '+375285554556', 'KH1245588', 2, 1500);
INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer2', '+375292221022', 'KH3455262', 3, 1000);
INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer3', '+375285554556', 'KH1245588', 2, 1500);
INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer4', '+375292221022', 'KH3455262', 3, 1000);

INSERT INTO car_status (status) VALUES ('AVAILABLE');
INSERT INTO car_status (status) VALUES ('RENTING');
INSERT INTO car_status (status) VALUES ('REPAIR');

INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('FORD', 'FOCUS', 2015, 'sedan', 'disel');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('AUDI', 'A6', 2019, 'hatchback', 'gas');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('VW', 'GOLF', 2017, 'hatchback', 'benzine');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('VW', 'PASSAT', 2019, 'cope', 'benzine');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('TESLA', 'MODEL 3', 2017, 'hatchback', 'electro');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('VW', 'GOLF', 2015, 'hatchback', 'disel');
INSERT INTO car_model (car_make, name, year, body_style, fuel)
VALUES ('GEELY', 'ATLAS', 2019, 'cope', 'benzine');

INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (1, 1, 50000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (2, 1, 100000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (3, 1, 200000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (4, 1, 100000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (5, 2, 15000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)
VALUES (6, 3, 80000);

INSERT INTO order_status (status) VALUES ('PENDING');
INSERT INTO order_status (status) VALUES ('EXECUTING');
INSERT INTO order_status (status) VALUES ('COMPLETED');
INSERT INTO order_status (status) VALUES ('CANCELED');
INSERT INTO order_status (status) VALUES ('NOT_PAID');

INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)
VALUES (1, 2, 3, '2021-05-01', '2021-05-05', 750, '');
INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)
VALUES (3, 3, 1, '2021-05-07', '2021-05-17', 200, '');
INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)
VALUES (2, 1, 5, '2021-05-07', '2021-05-17', 200, '');