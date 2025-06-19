FROM nginx
COPY ./docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf
HEALTHCHECK --start-period=10s --start-interval=1s CMD service nginx status || exit 1