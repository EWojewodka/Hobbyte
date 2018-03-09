CREATE TABLE hb_extranet_users(
	"hb_extranet_user_id" 	INTEGER NOT NULL PRIMARY KEY,
	"created_at" 			timestamp NOT NULL,
	"login"					VARCHAR(255) NOT NULL UNIQUE,
	"email"					VARCHAR(255) NOT NULL UNIQUE,
	"password"				VARCHAR(255) NOT NULL,
	"name"					VARCHAR(255),
	"lastname"				VARCHAR(255),
	"agreement"				SMALLINT DEFAULT 0,
	"age"					INTEGER
);

--Create id schema for integration with hibernate	
CREATE SEQUENCE hibernate_sequence  INCREMENT 1  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
	