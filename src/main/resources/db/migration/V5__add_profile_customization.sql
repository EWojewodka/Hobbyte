CREATE TABLE hb_extranet_user_cutomization(
	user_id INTEGER PRIMARY KEY REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE CASCADE,
	background_image_url 			VARCHAR(500),
	background_color 				VARCHAR(10),
	profile_image_border_color 		VARCHAR(10)
);