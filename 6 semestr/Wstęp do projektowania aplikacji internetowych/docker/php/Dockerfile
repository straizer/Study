FROM php:8.4.8-fpm-alpine
RUN echo 'memory_limit = -1' >> /usr/local/etc/php/conf.d/docker-php-memlimit.ini
RUN apk add --no-cache fcgi postgresql-dev
RUN docker-php-ext-install pdo pdo_pgsql
HEALTHCHECK --start-period=10s --start-interval=1s \
    CMD SCRIPT_NAME=/ping SCRIPT_FILENAME=/ping REQUEST_METHOD=GET cgi-fcgi -bind -connect 127.0.0.1:9000 || exit 1
