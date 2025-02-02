from datetime import datetime
from types import TracebackType

from bson import ObjectId
from bson.errors import InvalidId
from bson.timestamp import Timestamp
from flask import Flask, request
from pymongo import MongoClient
from pymongo.errors import ServerSelectionTimeoutError, NotPrimaryError
from werkzeug.exceptions import MethodNotAllowed
from werkzeug.routing import Map, Rule

app = Flask(__name__)
app.url_map.strict_slashes = False

mongo_clients = {
    "mongo1": MongoClient("mongodb://mongo1:27017/?directConnection=true", serverSelectionTimeoutMS=100),
    "mongo2": MongoClient("mongodb://mongo2:27017/?directConnection=true", serverSelectionTimeoutMS=100),
    "mongo3": MongoClient("mongodb://mongo3:27017/?directConnection=true", serverSelectionTimeoutMS=100),
    "rs": MongoClient("mongodb://mongo1:27017,mongo2:27017,mongo3:27017/?replicaSet=rs", serverSelectionTimeoutMS=100)
}


@app.route("/healthcheck", methods=["GET"])
def healthcheck():
    mc: MongoClient = get_mongo_client()
    return deep_stringify(mc.admin.command('replSetGetStatus'))


@app.route("/", methods=["GET"])
def home():
    return deep_stringify(app.url_map)


@app.route("/ping", methods=["GET"])
def ping():
    return deep_stringify(get_mongo_client().admin.command("ping"))


@app.route("/get/<collection_name>", methods=["GET"])
@app.route("/get/<collection_name>/<_id>", methods=["GET"])
def get(collection_name, _id=None):
    collection = get_collection(collection_name)
    if _id:
        return deep_stringify(collection.find_one({"_id": ObjectId(_id)}))
    else:
        return list(map(deep_stringify, collection.find()))


@app.route("/add/<collection_name>", methods=["POST"])
def add(collection_name, _id=None):
    get_collection(collection_name).insert_one(data := request.get_json())
    return {"message": "Data saved successfully", "data": deep_stringify(data)}


@app.errorhandler(MethodNotAllowed)
def handle_method_not_allowed(e: MethodNotAllowed):
    return get_error_body(
        f"Method {request.method} not allowed for URL <{request.url}>. Valid methods are: {e.valid_methods}"), 405


@app.errorhandler(NotPrimaryError)
def handle_not_primary_error(_):
    return get_error_body(f"Mongo client <{request.args.get("client")}> not primary."), 403


@app.errorhandler(InvalidId)
def handle_invalid_id(e):
    return get_error_body(e), 400


@app.errorhandler(ServerSelectionTimeoutError)
def handle_server_selection_timeout_error(e):
    return get_error_body(f"Mongo client <{request.args.get("client")}> not responding."), 404


@app.errorhandler(KeyError)
def handle_key_error(e):
    traceback: TracebackType = e.__traceback__
    while traceback.tb_frame.f_code.co_name != "get_mongo_client":
        if not (traceback := traceback.tb_next):
            raise e
    return get_error_body(f"Unknown mongo client: <{e.args[0]}>. Valid values are: {list(mongo_clients.keys())}."), 404


def get_mongo_client():
    return mongo_clients[request.args.get("client", default="rs")]


def get_collection(collection_name):
    return get_mongo_client()["defaultDB"][collection_name]


def get_error_body(message):
    return {"error": str(message)}


def deep_stringify(value):
    match value:
        case dict():
            return {k: deep_stringify(v) for k, v in value.items()}
        case list() | set():
            return [deep_stringify(entry) for entry in value]
        case bytes():
            return value.decode("utf-8")
        case str() | int() | float() | bool() | type(None):
            return value
        case Timestamp():
            return value.time
        case Map():
            return deep_stringify(list(value.iter_rules()))
        case Rule():
            return deep_stringify({"endpoint": str(value), "methods": value.methods})
        case datetime():
            return value.astimezone().isoformat()
        case _:
            print(f"{type(value)}: {value} -> {repr(value)}")
            return str(value)


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
