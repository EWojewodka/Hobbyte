ALTER TABLE hb_extranet_users 
	ADD COLUMN gender INTEGER,
	ADD COLUMN phone_number VARCHAR(20),
	ADD COLUMN newsletter INTEGER DEFAULT 0;
