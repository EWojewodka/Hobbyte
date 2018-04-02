ALTER TABLE hb_extranet_users 
	ADD COLUMN remember_me_code 
	VARCHAR(255) UNIQUE;