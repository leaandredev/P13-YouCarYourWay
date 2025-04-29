CREATE TABLE `language` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(255) UNIQUE,
  `name` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `country` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(10) UNIQUE,
  `name` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `first_name` VARCHAR(255),
  `last_name` VARCHAR(255),
  `language_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `client` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `birth_date` DATE,
  `address_1` VARCHAR(255),
  `address_2` VARCHAR(255),
  `postal_code` VARCHAR(255),
  `city` VARCHAR(255),
  `country_id` INT,
);

CREATE TABLE `support` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
);

ALTER TABLE `client` ADD FOREIGN KEY (`id`) REFERENCES `user`(`id`);
ALTER TABLE `support` ADD FOREIGN KEY (`id`) REFERENCES `user`(`id`);

ALTER TABLE `user` ADD FOREIGN KEY (`country_id`) REFERENCES `country`(`id`);

ALTER TABLE `user` ADD FOREIGN KEY (`language_id`) REFERENCES `language`(`id`);

CREATE TABLE `remember_me_tokens` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT UNIQUE,
  `selector` VARCHAR(255),
  `hashed_token` VARCHAR(255),
  `expires_at` DATETIME,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

ALTER TABLE `remember_me_tokens` ADD FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

CREATE TABLE `currency` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(255) UNIQUE,
  `symbol` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `rental_agency` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255),
  `address_1` VARCHAR(255),
  `address_2` VARCHAR(255),
  `postal_code` VARCHAR(255),
  `city` VARCHAR(255),
  `country_id` INT,
  `phone` VARCHAR(50),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `rental_agency` ADD FOREIGN KEY (`country_id`) REFERENCES `country`(`id`);

CREATE TABLE `vehicule_category` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `language_id` INT,
  `acriss_code` VARCHAR(10),
  `description` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `vehicule_category` ADD FOREIGN KEY (`language_id`) REFERENCES `language`(`id`);

CREATE TABLE `rental_offer` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `rental_agency_id` INT,
  `vehicle_vategory_id` INT,
  `name` VARCHAR(255),
  `description` TEXT,
  `price_per_day` DECIMAL(10, 2),
  `currency_id` INT,
  `available_from` DATETIME,
  `available_to` DATETIME,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `rental_offer` ADD FOREIGN KEY (`rental_agency_id`) REFERENCES `rental_agency`(`id`);
ALTER TABLE `rental_offer` ADD FOREIGN KEY (`vehicle_vategory_id`) REFERENCES `vehicule_category`(`id`);
ALTER TABLE `rental_offer` ADD FOREIGN KEY (`currency_id`) REFERENCES `currency`(`id`);

CREATE TABLE `rental_booking` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `client_id` INT,
  `rental_offer_id` INT,
  `departure_city` VARCHAR(255),
  `departure_datetime` DATETIME,
  `arrival_city` VARCHAR(255),
  `arrival_datetime` DATETIME,
  `price` DECIMAL(10, 2),
  `currency_id` INT,
  `status` ENUM('pending', 'confirmed', 'canceled', 'completed'),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `rental_booking` ADD FOREIGN KEY (`rental_offer_id`) REFERENCES `rental_offer`(`id`);
ALTER TABLE `rental_booking` ADD FOREIGN KEY (`client_id`) REFERENCES `client`(`id`);

CREATE TABLE `chat_session`( (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `client_id` INT,
  `support_id` INT,
  `external_thread_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `closed_at` TIMESTAMP DEFAULT NULL,
);

ALTER TABLE `chat_session` ADD FOREIGN KEY (`client_id`) REFERENCES `client`(`id`);
ALTER TABLE `chat_session` ADD FOREIGN KEY (`support_id`) REFERENCES `support`(`id`);

CREATE TABLE `chat_message` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `chat_session_id` INT,
  `sender_id` INT,
  `content` TEXT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `chat_message` ADD FOREIGN KEY (`chat_session_id`) REFERENCES `chat_session`(`id`);
ALTER TABLE `chat_message` ADD FOREIGN KEY (`sender_id`) REFERENCES `user`(`id`);