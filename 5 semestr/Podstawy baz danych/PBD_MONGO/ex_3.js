// noinspection JSUnresolvedReference,SpellCheckingInspection,JSNonASCIINames,MagicNumberJS

db.klienci.aggregate([
    {$match: {"imie": {$in: ["Adam", "Tomasz"]}}},
    {$group: {_id: "$imie", "ilość": {$sum: 1}}}]);

db.zakwaterowanie.aggregate([{$group: {_id: "$idKlienta", "koszt": {$sum: "$koszt"}}}]);

db.klienci.aggregate([
    {$match: {"imie": {$in: ["Adam", "Marta", "Beata"]}}},
    {$group: {_id: "$imie", "ilość": {$sum: 1}}},
    {$sort: {"ilość": -1}}]);

db.klienci.insertOne({
    "idKlienta": 100,
    "nazwisko": "Oplski",
    "imie": "Kamil",
    "nrDowodu": "OP9900222",
    "adresZamieszkania": {"miejscowosc": "Opole", "ulica": "Podkarpacka", "nrDomu": 44, "nrMieszkania": 678}
});
db.klienci.find({"idKlienta": 100});

db.klienci.insertMany([
    {
        "idKlienta": 200,
        "nazwisko": "Kaminski",
        "imie": "Teodor",
        "nrDowodu": "KT124546",
        "adresZamieszkania": {"miejscowosc": "Gdynia", "ulica": "Katowicka", "nrDomu": 89}
    },
    {
        "idKlienta": 201,
        "nazwisko": "Tokarczuk",
        "imie": "Eleonora",
        "nrDowodu": "TE4434352",
    }
]);
db.klienci.find({"idKlienta": {$in: [200, 201]}});

db.klienci.updateOne({"idKlienta": 100}, {$set: {"eMail": "opolK234@o3.pl"}});

db.pokoje.updateMany({"dostawka": "T", "cenaZaDobe": {$gte: 99, $lt: 210}}, {$set: {"cenaZaDobe": 2000}});

db.klienci.findOneAndUpdate({"eMail": {$regex: /com$/gm}, "komercyjny": {$exists: 0}}, {$set: {"komercyjny": 1}});

db.klienci.findOneAndReplace({"eMail": {$regex: /com$/gm}}, {"komercyjny": 1});

db.klienci.deleteMany({"eMail": {$exists: 0}});

db.createCollection("myCollection");

db.myCollection.insertMany([
    {
        "text": "a",
        "arr": [1, 2, 3],
        "date": new ISODate("2024-12-14"),
    },
    {
        "text": "b",
        "arr": [4, 5, 6],
        "date": new ISODate("2024-12-15"),
    },
    {
        "text": "c",
        "arr": [7, 8, 9],
        "date": new ISODate("2024-12-16"),
    },
]);

db.myCollection.updateMany({}, {$set: {"text": "Hello World"}});

db.myCollection.drop();

db.myCollection.find();
