from flask import Flask, request, jsonify
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

# Inicjalizacja aplikacji Flask
app = Flask(__name__)

# Konfiguracja połączenia z MongoDB
MONGO_URI = "mongodb://root:example@mongo:27017"
client = MongoClient(MONGO_URI)

# Sprawdzanie połączenia z bazą danych
try:
    client.admin.command('ping')
    print("Connection to MongoDB successful")
except ConnectionFailure:
    print("Connection to MongoDB failed")
    exit(1)

# Wybór bazy danych i kolekcji
db = client['mydatabase']
collection = db['people']


# Endpoint do dodawania imienia i nazwiska
@app.route('/add_name', methods=['POST'])
def add_name():
    data = request.get_json()
    print("New POST request to /add_name with payload:", data)

    # Sprawdzanie, czy dane są poprawne
    if not data or not 'name' in data or not 'surname' in data:
        response = {"error": "Missing data (name, surname)"}
        print(400, response, sep="\t")
        return jsonify(response), 400

    name = data['name']
    surname = data['surname']

    # Wstawianie danych do bazy
    person = {"name": name, "surname": surname}
    result = collection.insert_one(person)

    response = {"message": "Data was saved successfully", "data": {k: str(v) for k, v in person.items()}}
    print(200, response, sep="\t")
    return jsonify(response), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, use_reloader=True)
