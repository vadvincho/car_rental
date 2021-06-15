USE car_rental;

INSERT INTO role (name) VALUE ('ROLE_ADMIN');
INSERT INTO role (name) VALUE ('ROLE_USER');

INSERT INTO user (login, password, email, role_id)
VALUES ('admin', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2', 'admin@mail.com', 1);
INSERT INTO user (login, password, email, role_id)
VALUES ('vadzim', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2','vad@mail.com', 2);
INSERT INTO user (login, password, email, role_id)
VALUES ('alex', '$2a$10$YBEC9x5LP5Li1kPtzb8/pO.aHQyrBqPFgs.NbU./iTYSSwFEjtpm2',NULL, 2);

INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer1', '+375285554556','KH1245588', 2, 1500);
INSERT INTO customer (name, phone_number, passport, user_id, balance)
VALUES ('customer2', '+375292221022','KH3455262', 3, 1000);

INSERT INTO car_status (status) VALUE ('AVAILABLE');
INSERT INTO car_status (status) VALUE ('RENTING');
INSERT INTO car_status (status) VALUE ('REPAIR'); 
 
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

INSERT INTO order_status (status) VALUE ('PENDING');
INSERT INTO order_status (status) VALUE ('EXECUTING');
INSERT INTO order_status (status) VALUE ('COMPLETED');
INSERT INTO order_status (status) VALUE ('CANCELED');
INSERT INTO order_status (status) VALUE ('NOT_PAID');

INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)  
VALUES (1, 2, 3, '2021-05-01', '2021-05-05', 750, '');
INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)  
VALUES (1, 2, 2, '2021-05-30', '2021-06-10', 220, '');
INSERT INTO rental_order (car_id, customer_id, order_status_id, start_time, end_time, price, info)  
VALUES (1, 2, 1, '2021-05-31', '2021-06-05', 150, '');

