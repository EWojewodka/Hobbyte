CREATE TABLE app_agents(
	app_agent_id 		INTEGER PRIMARY KEY,
	code 				VARCHAR(100) NOT NULL UNIQUE,
	agent_class 		VARCHAR(255) NOT NULL,
	created_at			TIMESTAMP NOT NULL,
	next_run			TIMESTAMP,
	last_run 			TIMESTAMP,
	properties 			TEXT
);