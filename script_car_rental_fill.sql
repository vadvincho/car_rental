USE car_rental;

INSERT INTO role (name) VALUE ('ROLE_ADMIN');
INSERT INTO role (name) VALUE ('ROLE_USER');

INSERT INTO user (login, password, role_id)
VALUES ('admin', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 1);
INSERT INTO user (login, password, role_id)
VALUES ('vad', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 2);
INSERT INTO user (login, password, role_id)
VALUES ('alex', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 2);

INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer1', '+375285554556','KH1245588', 2, 1500);
INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer2', '+375292221022','KH3455262', 3, 1000);

INSERT INTO manager (name, phone_number, user_id)
VALUES ('manager', '+375285554556', 1); 

INSERT INTO car_status (status) VALUE ('AVAILABLE');
INSERT INTO car_status (status) VALUE ('RENTING');
INSERT INTO car_status (status) VALUE ('REPAIR'); 
 
INSERT INTO car_make (name, build_country)  VALUES ('FORD', 'USA');
INSERT INTO car_make (name, build_country)  VALUES ('VW', 'GERMANY');
INSERT INTO car_make (name, build_country)  VALUES ('AUDI', 'GERMANY');
INSERT INTO car_make (name, build_country)  VALUES ('MAZDA', 'JAPAN');
INSERT INTO car_make (name, build_country)  VALUES ('KIA', 'S.KOREA');

INSERT INTO car_model (car_make_id, name, year, body_style, fuel)  
VALUES (1, 'FOCUS', 2015, 'sedan', 'disel');
INSERT INTO car_model (car_make_id, name, year, body_style, fuel)  
VALUES (1, 'FOCUS', 2019, 'hatchback', 'gas');
INSERT INTO car_model (car_make_id, name, year, body_style, fuel)  
VALUES (2, 'GOLF', 2017, 'hatchback', 'benzine');
INSERT INTO car_model (car_make_id, name, year, body_style, fuel)  
VALUES (4, '3', 2019, 'cope', 'benzine');

INSERT INTO car (car_model_id, car_status_id, total_mileage)  
VALUES (1, 1, 40000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)  
VALUES (2, 1, 100000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)  
VALUES (3, 2, 15000);
INSERT INTO car (car_model_id, car_status_id, total_mileage)  
VALUES (4, 3, 80000);

INSERT INTO order_status (status) VALUE ('PENDING');
INSERT INTO order_status (status) VALUE ('EXECUTING');
INSERT INTO order_status (status) VALUE ('COMPLETED');
INSERT INTO order_status (status) VALUE ('CANCELED');

INSERT INTO car_damage (info, price)  
VALUES ('damage to paintwork', 3000);

INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info, car_damage_id)  
VALUES (1, 2, 3, '2021-05-01', '2021-05-05', 750, 'qqqqq', 1);

