alter table users add passport_id bigint;
alter table users add constraint passports_fkey foreign key(passport_id)
 references passports(id);