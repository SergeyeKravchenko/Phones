-- Dumping database structure for phonebook
CREATE DATABASE IF NOT EXISTS `phonebook`;
USE `phonebook`;

-- Dumping structure for table phonebook.user
CREATE TABLE user
(
  login    VARCHAR(255) NOT NULL
    PRIMARY KEY,
  fio      VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB;
  
-- Dumping structure for table phonebook.users
CREATE TABLE phone
(
  id             BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  address        VARCHAR(255) NULL,
  email          VARCHAR(255) NULL,
  firstname      VARCHAR(255) NOT NULL,
  lastname       VARCHAR(255) NOT NULL,
  middlename     VARCHAR(255) NOT NULL,
  phonemobile    VARCHAR(255) NOT NULL,
  phonepermanent VARCHAR(255) NULL,
  user_login     VARCHAR(255) NOT NULL,
  CONSTRAINT FKlex3qqcgxh7oglxpocyu5ap7j
  FOREIGN KEY (user_login) REFERENCES user (login)
)
  ENGINE = InnoDB;

CREATE INDEX FKlex3qqcgxh7oglxpocyu5ap7j
  ON phone (user_login);
  
  
  In file application.properties 
  uncomment property "spring.jpa.hibernate.ddl-auto"
  for first auto creating database.
  
  Filename of resources/application.properties is changed to application.properties_  
  for using external source.Place this file with right name application.properties in some 
  folder and edit run configuration of VM options : -Dlardi.conf=/path/to/application.properties
  