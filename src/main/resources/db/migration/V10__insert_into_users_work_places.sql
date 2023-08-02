create or replace procedure insert_users_work_places(employee_id bigint, work_place_id bigint)
as $$
begin
 insert into users_work_places(employees_id, work_places_id, active) values(
	 employee_id, work_place_id, false);
end;
$$ language plpgsql;

ALTER PROCEDURE public.insert_users_work_places(bigint, bigint)
    OWNER TO postgres;