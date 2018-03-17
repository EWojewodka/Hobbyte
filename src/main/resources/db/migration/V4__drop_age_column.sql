ALTER TABLE hb_extranet_users DROP COLUMN age;
ALTER TABLE hb_extranet_users 
	ADD COLUMN born DATE, 
	ADD COLUMN description TEXT;
	
CREATE TABLE hb_user2_user_follows(
	user_id INTEGER REFERENCES hb_extranet_users ON DELETE CASCADE,
	follow_user_id INTEGER REFERENCES hb_extranet_users ON DELETE CASCADE,
	created_at TIMESTAMP DEFAULT NOW()
);