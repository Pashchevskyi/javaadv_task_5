create or replace procedure delete_users_work_places(employee_id bigint, work_place_id bigint)
as $$
begin
 delete from users_work_places where employees_id = employee_id and work_places_id = work_place_id;
end;
$$ language plpgsql;

ALTER PROCEDURE public.delete_users_work_places(bigint, bigint)
    OWNER TO postgres;