CREATE TABLE IF NOT EXISTS workplaces
(
    id integer NOT NULL DEFAULT nextval('workplaces_id_seq'::regclass),
    air_condition boolean,
    coffee_machine boolean,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT workplaces_pkey PRIMARY KEY (id)
)