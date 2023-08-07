CREATE TABLE IF NOT EXISTS passports
(
    id bigint NOT NULL DEFAULT nextval('passports_id_seq'::regclass),
    body_handed character varying(255) COLLATE pg_catalog."default",
    expire_date timestamp without time zone,
    hand_date timestamp without time zone,
    "number" character varying(255) COLLATE pg_catalog."default",
    series character varying(255) COLLATE pg_catalog."default",
    uuid character varying(255) COLLATE pg_catalog."default",
    is_handed boolean,
    CONSTRAINT passports_pkey PRIMARY KEY (id)
)