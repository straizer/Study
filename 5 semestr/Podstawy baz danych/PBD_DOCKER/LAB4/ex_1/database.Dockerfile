FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=example
ENV MYSQL_DATABASE=ex_4
ENV MYSQL_INITDB_SKIP_TZINFO=true

COPY init.sql /docker-entrypoint-initdb.d/init.sql