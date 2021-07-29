# Evaluation schema
 
# --- !Ups
CREATE TABLE IF NOT EXISTS evaluation (
	id serial PRIMARY KEY,
	speaker VARCHAR(45),
	subject VARCHAR(200),
	date DATE NOT NULL,
	words bigint NOT NULL
);
# --- !Downs
drop table evaluation
