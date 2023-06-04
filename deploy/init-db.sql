\c postgres postgres

ALTER DATABASE postgres SET timezone TO 'Europe/Kiev';
SELECT pg_reload_conf();

DROP DATABASE IF EXISTS employee;

CREATE DATABASE employee
  WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c employee postgres