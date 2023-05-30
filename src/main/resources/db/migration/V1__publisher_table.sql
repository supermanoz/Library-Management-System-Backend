CREATE TABLE `publisher` (
  `publisher_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`publisher_id`)
);

CREATE TABLE `book` (
  `isbn` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `checkout_duration` int DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `publication_date` datetime(6) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `publisher_publisher_id` bigint DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  UNIQUE KEY `UK_g0286ag1dlt4473st1ugemd0m` (`title`),
  KEY `FKku84943ve4s7xdq8wunk6g2vf` (`publisher_publisher_id`),
  CONSTRAINT `FKku84943ve4s7xdq8wunk6g2vf` FOREIGN KEY (`publisher_publisher_id`) REFERENCES `publisher` (`publisher_id`)
);

CREATE TABLE `member` (
  `member_id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `image_data` longblob,
  `image_path` varchar(255) DEFAULT NULL,
  `issued_date` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`),
  CONSTRAINT `member_chk_1` CHECK ((`gender` in (_utf8mb4'm',_utf8mb4'f'))),
  CONSTRAINT `member_chk_2` CHECK ((`role` in (_utf8mb4'LIBRARIAN',_utf8mb4'STUDENT',_utf8mb4'STAFF')))
);

CREATE TABLE `member_seq` (
  `next_val` bigint DEFAULT NULL
);

CREATE TABLE `burrow` (
  `burrow_id` bigint NOT NULL AUTO_INCREMENT,
  `burrow_date` datetime(6) DEFAULT NULL,
  `fine_amount` double DEFAULT NULL,
  `fine_due` bit(1) DEFAULT NULL,
  `is_returned` bit(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `returned_date` datetime(6) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `member_id` int NOT NULL,
  PRIMARY KEY (`burrow_id`),
  KEY `FKh82y1jpme9iwi01i9pq0d120a` (`isbn`),
  KEY `FK3d2179cnwjnnkicjk1i2h30lp` (`member_id`),
  CONSTRAINT `FK3d2179cnwjnnkicjk1i2h30lp` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKh82y1jpme9iwi01i9pq0d120a` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`)
);

CREATE TABLE `request` (
  `request_id` bigint NOT NULL AUTO_INCREMENT,
  `request_date` datetime(6) DEFAULT NULL,
  `request_due` bit(1) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `member_id` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `FKfbtd4v3yhr40r247qjcy9suje` (`isbn`),
  KEY `FKlgy36p5youj5oe1irwbit23ct` (`member_id`),
  CONSTRAINT `FKfbtd4v3yhr40r247qjcy9suje` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`),
  CONSTRAINT `FKlgy36p5youj5oe1irwbit23ct` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
);