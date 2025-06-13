FROM postgres

ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB db
ENV POSTGRES_USER docker

COPY . /app/database

HEALTHCHECK --interval=10s --timeout=5s --start-period=10s --start-interval=1s --retries=5 CMD pg_isready -U ${POSTGRES_USER}