FROM mongo:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

HEALTHCHECK --interval=10s --timeout=5s --start-period=10s --start-interval=1s --retries=5 CMD echo "try { rs.status() } catch (err) { rs.initiate({_id:'shardaSet',members:[{_id:0,host:'sharda1:27017'},{_id:1,host:'sharda2:27017'}]}) }" | mongosh --quiet

CMD [ "mongod", "--port", "27017", "--shardsvr", "--replSet", "shardaSet", "--bind_ip_all" ]
