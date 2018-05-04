CREATE TABLE app_params(
	app_param_id INTEGER 	PRIMARY KEY,
	code 		VARCHAR(255) UNIQUE NOT NULL,
	value		TEXT
);