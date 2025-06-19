FROM postgres
ENV POSTGRES_PASSWORD password
HEALTHCHECK --start-period=10s --start-interval=1s CMD pg_isready -U postgres