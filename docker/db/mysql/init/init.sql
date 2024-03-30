GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `chess`;

CREATE TABLE IF NOT EXISTS `chessgame`
(
    `id`         BIGINT     NOT NULL AUTO_INCREMENT,
    `turn`       VARCHAR(8) NOT NULL,
    `isRunning`  TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `piece`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT,
    `position`      VARCHAR(8)  NOT NULL,
    `type`          VARCHAR(16) NOT NULL,
    `color`         VARCHAR(16) NOT NULL,
    `chessGameId`   BIGINT      NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `chessGameId` (`chessGameId` ASC) VISIBLE,
    FOREIGN KEY (`chessGameId`) REFERENCES `chessgame` (`id`) ON DELETE CASCADE
);
