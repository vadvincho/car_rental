DROP SCHEMA IF EXISTS car_rental;
CREATE SCHEMA IF NOT EXISTS car_rental CHARACTER SET utf8mb4;
USE car_rental;

CREATE TABLE IF NOT EXISTS role
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(30)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id  BIGINT,
    FOREIGN KEY (role_id) REFERENCES role (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS customer
(
    id 			BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name 		VARCHAR(30) NOT NULL,
    phone_number VARCHAR(15),
    passport 	VARCHAR(100),
    user_id  	BIGINT NOT NULL,
    balance 		DOUBLE,
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS manager
(
    id              BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(30) NOT NULL,
    phone_number    VARCHAR(15),
    user_id         BIGINT      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS car_status
(
    id     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS car_make
(
    id      BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(30) NOT NULL,
    build_country VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS car_model
(
    id 				BIGINT 		NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_make_id 	BIGINT 		NOT NULL,
	name 			VARCHAR(45) NOT NULL,
	year 			INT 		NOT NULL,
	body_style 		VARCHAR(45),
	fuel 			VARCHAR(45),
    FOREIGN KEY (car_make_id) REFERENCES car_make (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS car
(
	id 				BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	car_model_id 	BIGINT NOT NULL,
	car_status_id 	BIGINT NOT NULL,
	total_mileage 	INT NOT NULL,
    FOREIGN KEY (car_model_id) REFERENCES car_model (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (car_status_id) REFERENCES car_status (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS order_status (
	id 		BIGINT 		NOT NULL AUTO_INCREMENT PRIMARY KEY,
	status 	VARCHAR(30)
    );
    
    CREATE TABLE IF NOT EXISTS car_damage (
	id 		BIGINT 		NOT NULL AUTO_INCREMENT PRIMARY KEY,
	info 	VARCHAR(200),
    price   DOUBLE
    );

CREATE TABLE IF NOT EXISTS rental_order (
	id 				BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	car_id 			BIGINT NOT NULL,
	customer_id 	BIGINT NOT NULL,
	order_status_id BIGINT NOT NULL,
	start_time 		DATE NOT NULL,
	end_time 		DATE NOT NULL,
	price 			DOUBLE,
	info 			VARCHAR(200),
    car_damage_id	BIGINT,
    FOREIGN KEY (car_id) REFERENCES car (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
    FOREIGN KEY (order_status_id) REFERENCES order_status (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (car_damage_id) REFERENCES car_damage (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
    );