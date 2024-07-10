CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `authority`(`group_id`,`name`)
VALUES(<group_id_value>, 'INVENTO_ADMIN');

INSERT INTO `authority`(`group_id`,`name`)
VALUES(<group_id_value>, 'INVENTO_WRITE');

INSERT INTO `authority`(`group_id`,`name`)
VALUES(<group_id_value>, 'INVENTO_READ');