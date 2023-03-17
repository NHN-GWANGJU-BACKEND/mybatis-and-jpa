CREATE TABLE IF NOT EXISTS `Users`
(
    `user_id`  VARCHAR(45)  NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `userName` VARCHAR(45)  NOT NULL,
    `verify`   INT          NOT NULL DEFAULT '1',
    PRIMARY KEY (`user_id`)
);

MERGE INTO `Users` KEY (`user_id`) VALUES ('admin', 'adminadmin', 'admin', 9);
MERGE INTO `Users` KEY (`user_id`) VALUES ('defaultModifier', '!ksdh/324vx@!# klsdzxj@#$#@ %%#@', '없음', 8);
MERGE INTO `Users` KEY (`user_id`) VALUES ('manty', '12345', '만티강사', 1);
MERGE INTO `Users` KEY (`user_id`) VALUES ('taewon', '12345', '임태원', 1);

CREATE TABLE IF NOT EXISTS `Article`
(
    `article_id` INT          NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(100) NOT NULL,
    `content`    TEXT         NOT NULL,
    `createdAt`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateAt`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `isDelete`   VARCHAR(2)      NOT NULL DEFAULT 'N',
    `replyCount` INT          NOT NULL DEFAULT '0',
    PRIMARY KEY (`article_id`)
);

MERGE INTO `Article` KEY (`article_id`) VALUES (0, 'test', 'test', '2022-11-29 10:30:00', '2022-11-29 10:30:00', 'N', 3);
MERGE INTO `Article` KEY (`article_id`) VALUES (1, 'test2', 'test2', '2022-11-29 10:40:00', '2022-11-29 10:40:00', 'N',
                                                1);
MERGE INTO `Article` KEY (`article_id`) VALUES (2, 'test3', 'test3', '2022-11-29 10:50:00', '2022-11-29 10:50:00', 'N',
                                                1);
MERGE INTO `Article` KEY (`article_id`) VALUES (3, 'test4', 'test4', '2022-11-29 10:10:00', '2022-11-29 10:10:00', 'Y',
                                                0);
MERGE INTO `Article` KEY (`article_id`) VALUES (4, 'test5', 'test5', '2022-11-29 10:20:00', '2022-11-29 10:20:00', 'Y',
                                                0);


CREATE TABLE IF NOT EXISTS `Heart`
(
    `article_id` INT         NOT NULL,
    `user_id`    VARCHAR(45) NOT NULL,

    PRIMARY KEY (`article_id`, `user_id`)
);

MERGE INTO `Heart` KEY (`article_id`, `user_id`) VALUES (0, 'admin');
MERGE INTO `Heart` KEY (`article_id`, `user_id`) VALUES (0, 'taewon');
MERGE INTO `Heart` KEY (`article_id`, `user_id`) VALUES (0, 'manty');
MERGE INTO `Heart` KEY (`article_id`, `user_id`) VALUES (1, 'admin');
MERGE INTO `Heart` KEY (`article_id`, `user_id`) VALUES (2, 'admin');

CREATE TABLE IF NOT EXISTS `Board`
(
    `board_id`      INT         NOT NULL,
    `article_id`    INT         NOT NULL,
    `createUser_id` VARCHAR(45) NOT NULL,
    `modifyUser_id` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`board_id`)
);

MERGE INTO `Board` KEY (`board_id`) VALUES (0, 0, 'admin', 'defaultModifier');
MERGE INTO `Board` KEY (`board_id`) VALUES (1, 1, 'taewon', 'defaultModifier');
MERGE INTO `Board` KEY (`board_id`) VALUES (2, 2, 'manty', 'defaultModifier');
MERGE INTO `Board` KEY (`board_id`) VALUES (3, 3, 'taewon', 'defaultModifier');
MERGE INTO `Board` KEY (`board_id`) VALUES (4, 4, 'manty', 'defaultModifier');


CREATE TABLE IF NOT EXISTS `Reply`
(
    `reply_id`   INT          NOT NULL AUTO_INCREMENT,
    `user_id`    VARCHAR(45)  NULL     DEFAULT NULL,
    `article_id` INT          NOT NULL,
    `content`    VARCHAR(150) NOT NULL,
    `createdAt`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`reply_id`)
);

MERGE INTO `Reply` KEY (`reply_id`) VALUES (0, 'admin', 0, 'test', '2022-11-29 10:30:00');
MERGE INTO `Reply` KEY (`reply_id`) VALUES (1, 'admin', 0, 'test', '2022-11-29 10:30:00');
MERGE INTO `Reply` KEY (`reply_id`) VALUES (2, 'admin', 0, 'test', '2022-11-29 10:30:00');
MERGE INTO `Reply` KEY (`reply_id`) VALUES (3, 'admin', 1, 'test', '2022-11-29 10:30:00');
MERGE INTO `Reply` KEY (`reply_id`) VALUES (4, 'admin', 2, 'test', '2022-11-29 10:30:00');

CREATE TABLE IF NOT EXISTS `ImageFile`
(
    `image_file_id`    INT          NOT NULL AUTO_INCREMENT,
    `article_id`       INT          NOT NULL,
    `originalFileName` VARCHAR(100) NOT NULL,
    `saveFileName`     VARCHAR(200) NOT NULL,
    `saveDirectory`    VARCHAR(200) NOT NULL,
    PRIMARY KEY (`image_file_id`)
);