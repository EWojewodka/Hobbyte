CREATE TABLE app_exceptions(
	app_exception_id 	INTEGER PRIMARY KEY,
	title				VARCHAR(500),
	content 			TEXT,
	created_at			TIMESTAMP DEFAULT NOW(),
	user_id				INTEGER REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE SET NULL
);