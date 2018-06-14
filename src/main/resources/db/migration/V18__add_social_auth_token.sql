ALTER TABLE hb_extranet_users 
	ADD COLUMN registration_type INTEGER DEFAULT 0,
	ADD COLUMN social_auth VARCHAR(500);
