
CREATE TABLE account (
	id int8 NOT NULL,
	date_created timestamp NULL,
	date_modified timestamp NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	alias varchar(128) NULL,
	email varchar(256) NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE account_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;