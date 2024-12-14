// noinspection JSUnresolvedReference,SpellCheckingInspection

db.klienci.find();

db.klienci.find({"nazwisko": "Kowalski"});

db.klienci.find({"adresZamieszkania.nrDomu": {$gt: 30, $lt: 70}});

db.klienci.find({"eMail": {$regex: /^N/gm}});

db.klienci.find({"imie": {$regex: /^adam$/gmi}});

db.klienci.find({"eMail": {$regex: /com$/gm}});

db.pokoje.find({"dostawka": "T"});

db.zakwaterowanie.find({}, {_id: 0, idZakwaterowania: 1, dataZakwaterowania: 1, dataWykwaterowania: 1});

db.pokoje.find({"pietro": 2, "liczbaMiejsc": 1, "dostawka": "T"});

db.klienci.find({"imie": {$in: ["Adam", "Marta", "Beata"]}});

db.klienci.find({"imie": {$nin: ["Adam", "Marta", "Beata"]}}).sort({"nazwisko": 1});

db.klienci.find({}, {"_id": 0, "nazwisko": true, "imie": 1, "idKlienta": 1}).sort({"nazwisko": 1, "imie": -1});

db.zakwaterowanie.find({"koszt": {$exists: 1}}).sort({"koszt": -1});

db.pokoje.find({"dostawka": "T", "cenaZaDostawke": {$elemMatch: {"liczbaDostawek": 1, "cenaZaDobe": {$gt: 50}}}});

db.pokoje.find({"dostawka": "T", "cenaZaDobe": {$gt: 300}}, {_id: 0, idPokoju: 1}).sort({"cenaZaDobe": -1});

db.klienci.find({"adresZamieszkania.miejscowosc": "Warszawa", "adresZamieszkania.ulica": "Polna"});
