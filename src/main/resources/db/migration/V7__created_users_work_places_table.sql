CREATE TABLE IF NOT EXISTS public.users_work_places
(
    employees_id bigint NOT NULL,
    work_places_id bigint NOT NULL,
    active boolean,
    CONSTRAINT users_work_places_pkey PRIMARY KEY (employees_id, work_places_id),
    CONSTRAINT fk5ftmy5w74b4bfckeqrw25f1gt FOREIGN KEY (employees_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fktayt07t7istr4gmtan4a1wwbe FOREIGN KEY (work_places_id)
        REFERENCES public.workplaces (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users_work_places
    OWNER to postgres;