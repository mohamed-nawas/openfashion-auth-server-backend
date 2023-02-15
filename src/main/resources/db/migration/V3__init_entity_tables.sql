SET
sql_mode = '';

CREATE TABLE `role`
(
    `id`      varchar(255) NOT NULL,
    `created` datetime DEFAULT NULL,
    `name`    varchar(255) NOT NULL,
    `updated` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user`
(
    `id`       varchar(255) NOT NULL,
    `created`  datetime DEFAULT NULL,
    `email`    varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `updated`  datetime DEFAULT NULL,
    `username` varchar(255) NOT NULL,
    `role_id`  varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY        `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
    CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;