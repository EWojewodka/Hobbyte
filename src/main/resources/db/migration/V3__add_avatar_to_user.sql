ALTER TABLE hb_extranet_users 
ADD COLUMN "image_url" VARCHAR(500);

CREATE TABLE hb_extranet_user_policy(
	user_id INTEGER PRIMARY KEY,
	profile_visible INTEGER
	
	CONSTRAINT hb_extranet_users_fk
	REFERENCES hb_extranet_users(hb_extranet_user_id) 
	ON DELETE CASCADE
);