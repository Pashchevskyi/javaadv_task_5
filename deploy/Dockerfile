RUN echo export PGPASSWORD='pgpasswd'; docker exec -it deploy-postgres_service-1 \
    psql -h localhost -U postgres -f /docker-entrypoint-initdb.d/init.sql