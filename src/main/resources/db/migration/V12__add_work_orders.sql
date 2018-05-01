CREATE TABLE hb_workers(
	hb_worker_id 	INTEGER PRIMARY KEY,
	executor_class 	VARCHAR(255),
	last_run 		INTEGER,
	next_run		INTEGER,
	properties		TEXT
);