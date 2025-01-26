// noinspection JSUnresolvedReference

db.klienci.find({"eMail": {$regex: /^..[ap]/gm}});

db.klienci.find({"eMail": {$regex: /o2/gm}}, {_id: 0, "nrDowodu": 1});

db.pokoje.find({"dostawka": "T", "cenaZaDostawke": {$elemMatch: {"cenaZaDobe": {$gt: 50, $lt: 80}}}});

db.pokoje.find({"pietro": 2}).sort({"pietro": 1}).limit(3);

db.pokoje.find({"pietro": 2}).sort({"pietro": 1}).skip(2).limit(4);

db.pokoje.distinct("pietro");

db.pokoje.find({"cenaZaDobe": {$lte: 200}}, {_id: 0, idPokoju: 1, liczbaMiejsc: 1, cenaZaDobe: 1});

db.zakwaterowanie.find({"dataZakwaterowania": ISODate("2024-02-23T17:13:03")}, {_id: 0, "idKlienta": 1});

db.zakwaterowanie.find({
    $or: [
        {"dataZakwaterowania": {$gte: ISODate("2024-01-01"), $lt: ISODate("2024-02-13")}},
        {"dataWykwaterowania": {$gte: ISODate("2024-01-01"), $lt: ISODate("2024-02-13")}}]
});

db.pokoje.find({"cenaZaDostawke.1.cenaZaDobe": 100});

db.pokoje.find({"cenaZaDostawke.0.cenaZaDobe": {$gte: 20, $lte: 80}});

db.pokoje.find({"cenaZaDostawke": {$type: "array"}});

db.pokoje.find({"liczbaMiejsc": {$mod: [2, 0]}});

// min/max are aggregate functions, they don't exist in find API
db.pokoje.find({"cenaZaDobe": {$gte: 400, $lte: 1000}});
