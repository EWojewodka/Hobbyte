CREATE TABLE hb_post_entries(
	hb_post_entry_id 	INTEGER PRIMARY KEY,
	author_id 			INTEGER REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE SET NULL, --Not delete on user entry. It will be annonymouse post.
	content 			TEXT,
	created_at 			TIMESTAMP NOT NULL
);

CREATE TABLE hb_post_entry_reaction(
	hb_post_entry_reaction_id 	INTEGER PRIMARY KEY,
	type 						VARCHAR(25),
	user_id 					INTEGER REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE CASCADE, --Delete with users
	post_entry_id 				INTEGER REFERENCES hb_post_entries(hb_post_entry_id) ON DELETE CASCADE, --Delete with entry
	created_at 					TIMESTAMP NOT NULL
);