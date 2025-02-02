FROM mongo:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

HEALTHCHECK --interval=10s --timeout=5s --start-period=10s --start-interval=1s --retries=5 CMD echo "try { rs.status() } catch (err) { rs.initiate({_id:'configSet',configsvr:true,members:[{_id:0,host:'config1:27017'},{_id:1,host:'config2:27017'}]}) }" | mongosh --quiet

CMD [ "mongod", "--port", "27017", "--configsvr", "--replSet", "configSet", "--bind_ip_all" ]
