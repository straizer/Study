FROM mongo:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --start-interval=1s --retries=5 CMD echo "let shards = db.adminCommand('getShardMap').map; try { shards.shardaSet.length; shards.shardbSet.length; } catch (err) { sh.addShard('shardaSet/sharda1:27017,sharda2:27017'); sh.addShard('shardbSet/shardb1:27017,shardb2:27017'); sh.shardCollection( 'defaultDB.table1', { '_id' : 'hashed' } ) }" | mongosh --quiet

CMD [ "mongos", "--port", "27017", "--configdb", "configSet/config1:27017,config2:27017", "--bind_ip_all" ]

#PS C:\Users\huber\repos> docker exec -it router1 mongosh
#Current Mongosh Log ID: 679f4318cf9e32274ae94969
#Connecting to:          mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.3.4
#Using MongoDB:          8.0.4
#Using Mongosh:          2.3.4
#
#For mongosh info see: https://www.mongodb.com/docs/mongodb-shell/
#
#
#To help improve our products, anonymous usage data is collected and sent to MongoDB periodically (https://www.mongodb.com/legal/privacy-policy).
#You can opt-out by running the disableTelemetry() command.
#
#------
#   The server generated these startup warnings when booting
#   2025-02-02T10:02:08.040+00:00: Access control is not enabled for the database. Read and write access to data and configuration is unrestricted
#------
#
#[direct: mongos] test> use defaultDB
#switched to db defaultDB
#[direct: mongos] defaultDB> db.create
#db.createUser                 db.createCollection           db.createEncryptedCollection  db.createView
#db.createRole
#
#[direct: mongos] defaultDB> db.createCollection
#db.createCollection
#
#[direct: mongos] defaultDB> db.createCollection(table1)
#ReferenceError: table1 is not defined
#[direct: mongos] defaultDB> db.createCollection(table1)
#[direct: mongos] defaultDB> show collections
#
#[direct: mongos] defaultDB> db.table1.insertOne({"name": "Hubert", "surname"})
#Uncaught:
#SyntaxError: Unexpected token (1:48)
#
#> 1 | db.table1.insertOne({"name": "Hubert", "surname"})
#    |                                                 ^
#  2 |
#
#[direct: mongos] defaultDB> db.table1.insertOne({"name": "Hubert", "surname": "Romaniak"})
#MongoServerError[ShardNotFound]: Database defaultDB could not be created :: caused by :: No non-draining shard found
#[direct: mongos] defaultDB> db.printShardingStatus()
#shardingVersion
#{ _id: 1, clusterId: ObjectId('679f42acd363bb7f37070c89') }
#---
#shards
#[]
#---
#active mongoses
#[]
#---
#autosplit
#{ 'Currently enabled': 'yes' }
#---
#balancer
#{
#  'Currently enabled': 'yes',
#  'Currently running': 'no',
#  'Failed balancer rounds in last 5 attempts': 0,
#  'Migration Results for the last 24 hours': 'No recent migrations'
#}
#---
#shardedDataDistribution
#[]
#---
#databases
#[
#  {
#    database: { _id: 'config', primary: 'config', partitioned: true },
#    collections: {}
#  }
#]
#[direct: mongos] defaultDB> sh.shardCollection("defaultDB.table1", {"_id": "hashed"})
#MongoServerError[ShardNotFound]: Database defaultDB could not be created :: caused by :: No non-draining shard found
#[direct: mongos] defaultDB> sh.enableSharding("defaultDB")
#MongoServerError[ShardNotFound]: Database defaultDB could not be created :: caused by :: No non-draining shard found
#[direct: mongos] defaultDB> exit
#
#What's next:
#    Try Docker Debug for seamless, persistent debugging tools in any container or image → docker debug router1
#    Learn more at https://docs.docker.com/go/debug-cli/