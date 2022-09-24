CREATE DATABASE `ecommerce_project`;

USE `ecommerce_project`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` char(80) DEFAULT NULL,
  `full_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `credit_card_type` varchar(20) DEFAULT NULL,
  `credit_card_no` varchar(30) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `expired_date` varchar(6) DEFAULT NULL,
  `cvv` varchar(6) DEFAULT NULL,
  `default_flag` tinyint(1) DEFAULT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `FK_user_payment_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
   PRIMARY KEY (`id`)
);


CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
   PRIMARY KEY (`user_id`, `role_id`),
   CONSTRAINT `FK_user_role_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
   CONSTRAINT `FK_user_role_roleId` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `course_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
  PRIMARY KEY (`id`),
);

INSERT INTO `course_type` (`id`,`name`,`create_date`) VALUES (1,'前端','2022-09-03 18:59:21');
INSERT INTO `course_type` (`id`,`name`,`create_date`) VALUES (2,'資料庫','2022-09-03 18:59:21');
INSERT INTO `course_type` (`id`,`name`,`create_date`) VALUES (3,'後端','2022-09-03 18:59:21');



CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `discount_price` int(11) DEFAULT NULL,
  `create_date` timestamp DEFAULT NULL,
  `instructor_id` int(11) NOT NULL,
  `image` mediumblob,
  `course_type_id` int NOT NULL,  
   PRIMARY KEY (`id`),
   CONSTRAINT `FK_course_instructorId` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
   CONSTRAINT `fk_course_type_id` FOREIGN KEY (`course_type_id`) REFERENCES `course_type` (`id`)
);

INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (1,'Vue - The Complete Guide','Vue.js is an awesome JavaScript Framework for building Frontend Applications! VueJS mixes the Best of Angular + React!',3600,490,'2022-09-01 22:36:22',1,NULL,1);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (2,'React - The Complete Guide','Dive in and learn React.js from scratch! Learn Reactjs, Hooks, Redux, React Routing, Animations, Next.js and way more!',2600,360,'2022-09-01 22:36:22',1,NULL,1);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (3,'Modern HTML & CSS From The Beginning (Including Sass)',NULL,2600,-1,'2022-09-01 22:36:22',1,NULL,1);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (4,'SQL - MySQL for Data Analytics and Business Intelligence','SQL that will get you hired – SQL for Business Analysis, Marketing, and Data Management',5000,590,'2022-09-01 22:36:22',2,NULL,2);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (5,'The Complete Developers Guide to MongoDB','Master MongoDB and Mongoose design with a test-driven approach',2500,330,'2022-09-01 22:36:22',2,NULL,2);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (6,'Java Programming Masterclass covering Java 11 & Java 17','Learn Java In This Course And Become a Computer Programmer. Obtain valuable Core Java Skills And Java Certification',3600,-1,'2022-09-01 22:36:22',3,NULL,3);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (7,'JSP, Servlets and JDBC for Beginners: Build a Database App','JSP: Covers JSP 2.3 and Servlets 3.1 - Most Popular JSP/Servlet course',2300,330,'2022-09-01 22:36:22',3,NULL,3);
INSERT INTO `course` (`id`,`name`,`description`,`price`,`discount_price`,`create_date`,`instructor_id`,`image`,`course_type_id`) VALUES (8,'Spring & Hibernate for Beginners (includes Spring Boot)','Spring 5: Learn Spring 5 Core, AOP, Spring MVC, Spring Security, Spring REST, Spring Boot 2, Thymeleaf, JPA & Hibernate',3500,490,'2022-09-01 22:36:22',3,NULL,3);


CREATE TABLE `instructor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `image` mediumblob,
  `create_date` timestamp DEFAULT NULL,
  `education` varchar(80) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
);
INSERT INTO `instructor` (`id`,`full_name`,`email`,`create_date`,`image`,`education`,`experience`) VALUES (1,'Susan Lee','susan.lee9203@gmail.com','2022-09-01 21:22:08',NULL,'台大資工系','2016-10~2018-09 驊碩初級工程師 2018-11~2022-08 google高級工程師');
INSERT INTO `instructor` (`id`,`full_name`,`email`,`create_date`,`image`,`education`,`experience`) VALUES (2,'Danny Chen','danny.chen9208@gmail.com','2022-09-01 21:22:08',NULL,'清大數學系','2012-01~2014-05 開心補習班數學老師 2014-11~2016-08 夏拉科技初級工程師 2016-09~2019-09 瞎皮中級工程師 2019-10~ google高級工程師');
INSERT INTO `instructor` (`id`,`full_name`,`email`,`create_date`,`image`,`education`,`experience`) VALUES (3,'Ken Lin','ken.lin9211@gmail.com','2022-09-01 21:22:08',NULL,'麻省理工學院電機系','2015-10~ google高級工程師');


CREATE TABLE review(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(3) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  `create_date` timestamp DEFAULT NULL,
  `user_id` int DEFAULT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `FK_review_courseId` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
   CONSTRAINT `FK_review_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `course_size` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `prices` longtext DEFAULT NULL,
  `create_time` timestamp DEFAULT NULL,
  `credit_card_no` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);



CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp DEFAULT NULL,
  `checkout_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_cart_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `orders_courses` (
  `order_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
   PRIMARY KEY (`order_id`, `course_id`),
   CONSTRAINT `FK_orders_courses_orderId` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
   CONSTRAINT `FK_orders_courses_courseId` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
);


CREATE TABLE `carts_courses` (
  `cart_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
   PRIMARY KEY (`cart_id`, `course_id`),
   CONSTRAINT `FK_carts_courses_cartId` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
   CONSTRAINT `FK_carts_courses_courseId` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
);

