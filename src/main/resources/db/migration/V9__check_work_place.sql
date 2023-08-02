create or replace function check_workplaces()
 returns trigger
as $$
declare employee_work_places_cnt bigint;
begin
 select count(work_places_id) into employee_work_places_cnt from users_work_places group by employees_id
 having count(work_places_id) >= 3;
 if (employee_work_places_cnt >= 3) then
  RAISE EXCEPTION 'No more than 3 workplaces for 1 employee.';
 end if;
return NEW;
end;
$$ language plpgsql;

create or replace trigger trigger_check_workplaces_more_than_3
 before insert on users_work_places
 for each row
execute function check_workplaces();