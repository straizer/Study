FROM mongo:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --start-interval=1s --retries=5 CMD echo "try { rs.status() } catch (err) { rs.initiate({_id:'rs',members:[{_id:0,host:'mongo1:27017'},{_id:1,host:'mongo2:27017'},{_id:2,host:'mongo3:27017'}]}) }" | mongosh --quiet

CMD [ "mongod", "--replSet", "rs", "--bind_ip_all" ]
