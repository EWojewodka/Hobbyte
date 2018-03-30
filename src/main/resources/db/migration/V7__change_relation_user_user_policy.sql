DROP TABLE hb_extranet_user_policy;

CREATE TABLE hb_extranet_user_policy(
	hb_extranet_user_policy_id 					INTEGER PRIMARY KEY,
	user_id 			INTEGER NOT NULL  REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE CASCADE,
	profile_visibility	INTEGER
);