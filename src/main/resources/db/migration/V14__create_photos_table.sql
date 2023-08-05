CREATE TABLE IF NOT EXISTS photos
(
    id bigint NOT NULL DEFAULT nextval('photos_id_seq'::regclass),
    date timestamp without time zone,
    href character varying(255) COLLATE pg_catalog."default",
    is_attached boolean,
    CONSTRAINT photos_pkey PRIMARY KEY (id)
)