GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS `chess_test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `chess`;

CREATE TABLE IF NOT EXISTS `chessgame`
(
    `id`     BIGINT      NOT NULL AUTO_INCREMENT,
    `turn`   VARCHAR(8)  NOT NULL,
    `pieces` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
);

USE `chess_test`;

CREATE TABLE IF NOT EXISTS `chessgame`
(
    `id`     BIGINT      NOT NULL AUTO_INCREMENT,
    `turn`   VARCHAR(8)  NOT NULL,
    `pieces` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
);
