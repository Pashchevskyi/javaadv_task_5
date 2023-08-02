create or replace function check_employees_country()
 returns trigger
as $$
begin
 if NEW.country is null then
  raise exception 'Contry cannot be null';
 end if;
 if NEW.country in ('RU') then
  RAISE EXCEPTION 'Unable to apply quassab for a job.';
 end if;
 if NEW.country not in ('AU','AT','AZ','AX','AL','DZ','AS','AI','AO','AD','AQ','AG','AR','AW',
 'AF','BS','BD','BB','BH','BZ','BE','BJ','BM','BY','BG','BO','BA','BW','BR','IO','BN','BF','BI',
 'BT','VU','VE','VN','VG','VI','AM','GA','HT','GY','GM','GH','GP','GT','GN','GW','GG','GI','HN',
 'HK','GD','GL','GR','GE','GU','DK','CD','JE','DJ','DM','DO','EC','GQ','ER','EE','ET','EG','YE',
 'ZM','EH','ZW','IL','IN','ID','IQ','IR','IE','IS','ES','IT','JO','CV','KZ','KH','CM','CA','QA',
 'KE','KG','CN','CY','KI','CC','CO','KM','CG','KP','KR','CR','CI','CU','KW','LA','LV','LS','LT',
 'LR','LB','LY','LI','LU','MU','MR','MG','YT','MO','MK','MW','MY','ML','UM','MV','MT','MA','MQ',
 'MH','MX','FM','MZ','MD','MC','MN','MS','MM','NA','NR','NP','NE','NG','NL','AN','NI','DE','NU',
 'NZ','NC','NO','TZ','AE','OM','BV','IM','NF','CX','SH','HM','KY','CK','SJ','PK','PW','PS','PA',
 'VA','PG','PY','PE','ZA','GS','MP','PN','PL','PT','PR','RE','RU','RW','RO','SV','WS','SM','ST',
 'SA','SZ','SC','SN','PM','VC','KN','LC','RS','SG','SY','SK','SI','SB','SO','GB','US','SD','SR',
 'SL','TJ','TH','TW','TC','TL','TG','TK','TO','TT','TV','TN','TR','TM','UG','HU','UZ','UA','WF',
 'UY','FO','FJ','PH','FI','FK','FR','GF','PF','TF','HR','CF','TD','CZ','CL','ME','CH','SE','LK',
 'JM','JP') then
  raise exception 'Unable to recognize country by Alpha-2 code of ISO 3166';
 end if;
 return NEW;
end;
$$ language plpgsql;

create or replace trigger trigger_user_country_null
 before insert or update on users
 for each row
execute function check_employees_country();