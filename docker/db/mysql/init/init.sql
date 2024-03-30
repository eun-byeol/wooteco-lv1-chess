GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `chess`;

CREATE TABLE IF NOT EXISTS `chessgame`
(
    `id`        BIGINT     NOT NULL AUTO_INCREMENT,
    `turn`      VARCHAR(8) NOT NULL,
    `isRunning` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `piece`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `positionRow`    INT         NOT NULL,
    `positionColumn` INT         NOT NULL,
    `piece`          VARCHAR(16) NOT NULL,
    `chessGameId`    BIGINT      NOT NULL,
    PRIMARY KEY (`id`)
);
