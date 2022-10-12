DROP SCHEMA IF EXISTS `new_project` ;

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `new_project` DEFAULT CHARACTER SET utf8 ;
USE `new_project` ;

DROP TABLE IF EXISTS `new_project`.`user` ;
DROP TABLE IF EXISTS `new_project`.`tariff` ;
DROP TABLE IF EXISTS `new_project`.`service` ;

CREATE TABLE `new_project`.`user` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `login` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` INTEGER NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `new_project`.`service` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `new_project`.`tariff` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) UNIQUE NOT NULL,
    `description` TEXT NOT NULL,
    `cost` INT NOT NULL,
    `frequency_of_payment` INTEGER NOT NULL,
    `type` INTEGER NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`type`) REFERENCES `service`(`id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;


INSERT INTO `user`
(`id`, `email`, `login`,   `password`, `role`)
VALUES
(1, "test1@gmail.com", "user1",   "12345",    0),
(2, "test2@gmail.com", "user2",   "12345",    0),
(3, "manager@gmail.com", "manager", "12345",    1),
(4, "admin@gmail.com", "admin",   "12345",    2);

INSERT INTO `service` (id, type)
VALUES
(1, "Internet"),
(2, "IP-TV"),
(3, "Phone");

INSERT INTO `tariff`
(id, name, description, cost, frequency_of_payment, type)
VALUES
(1, "Home Internet 75", "speed 75 Mbit/s", 185, 28, 1),
(2, "Home Internet 100", "speed 100 Mbit/s", 250, 28, 1),
(3, "Fast Internet 250", "speed 250 Mbit/s", 280, 28, 1),
(4, "Television", "over 160 channels", 129, 28, 2),
(5, "Television+", "over 160 channels + free films", 181, 28, 2),
(6, "Homephone 60", "600 free minutes to local communication and 60 minutes long distance or mobile calls", 69, 28, 3),
(7, "Homephone 300", "1300 free minutes to local communication and 300 minutes long distance or mobile calls", 99, 28, 3);



