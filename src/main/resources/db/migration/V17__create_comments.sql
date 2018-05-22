CREATE TABLE hb_comments(
	hb_comment_id 		INTEGER PRIMARY KEY,
	content 			TEXT NOT NULL,
	created_at			DATE NOT NULL,
	author_id			INTEGER REFERENCES hb_extranet_users(hb_extranet_user_id) ON DELETE CASCADE,
	post_id				INTEGER REFERENCES hb_post_entries(hb_post_entry_id) ON DELETE CASCADE,
	parent_comment_id 	INTEGER REFERENCES hb_comments(hb_comment_id) ON DELETE CASCADE
);