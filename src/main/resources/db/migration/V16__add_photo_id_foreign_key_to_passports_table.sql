alter table passports add photo_id bigint;
alter table passports add constraint photos_fkey FOREIGN KEY (photo_id)
        REFERENCES photos (id);