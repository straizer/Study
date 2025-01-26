// noinspection JSUnresolvedReference,SpellCheckingInspection,IncorrectFormatting,LongLine

db.klienci.drop();
db.pokoje.drop();
db.zakwaterowanie.drop();

db.klienci.insertMany([
    {
        "idKlienta": 1,
        "nazwisko": "Kowalski",
        "imie": "Adam",
        "nrDowodu": "WW1234567",
        "eMail": "Adam_Kowlaski@wp.pl",
        "adresZamieszkania": {"miejscowosc": "Warszawa", "ulica": "Polna", "nrDomu": 2, "nrMieszkania": 31}
    },
    {
        "idKlienta": 2,
        "nazwisko": "Nowak",
        "imie": "Piotr",
        "nrDowodu": "CR2343561",
        "eMail": "NowakPiotr@o2.pl",
        "adresZamieszkania": {"miejscowosc": "Krakow", "ulica": "Pokoju", "nrDomu": 77}
    },
    {
        "idKlienta": 3,
        "nazwisko": "Adamowicz",
        "imie": "Marta",
        "nrDowodu": "AM8967452",
        "eMail": "wojtek222@wp.com",
        "adresZamieszkania": {"miejscowosc": "Gdansk", "ulica": "Poznanska", "nrDomu": 234}
    },
    {
        "idKlienta": 4,
        "nazwisko": "Ulman",
        "imie": "Bogdan",
        "nrDowodu": "UB3478690",
        "eMail": "wwwUlman@wp.pl",
        "adresZamieszkania": {"miejscowosc": "Katowice", "ulica": "Powstania", "nrDomu": 67, "nrMieszkania": 4}
    },
    {
        "idKlienta": 5,
        "nazwisko": "Lipowski",
        "imie": "Tomasz",
        "nrDowodu": "LT6712098",
        "eMail": "LipowskiT@wp.pl",
        "adresZamieszkania": {"miejscowosc": "Wieliczka", "ulica": "Ogrodowa", "nrDomu": 9}
    },
    {
        "idKlienta": 6,
        "nazwisko": "Tuskowski",
        "imie": "Beata",
        "nrDowodu": "TB4341234",
        "eMail": "tusek@wp.com",
        "adresZamieszkania": {"miejscowosc": "Warszawa", "ulica": "Polna", "nrDomu": 255}
    },
    {
        "idKlienta": 7,
        "nazwisko": "Barczyk",
        "imie": "Pola",
        "nrDowodu": "BP535930",
        "eMail": "polka@gmail.com",
        "adresZamieszkania": {"miejscowosc": "Warszawa", "ulica": "Topolowa", "nrDomu": 100}
    },
    {
        "idKlienta": 8,
        "nazwisko": "Kowalski-Redi",
        "imie": "adam",
        "nrDowodu": "KA903552",
        "eMail": "kra@gmail.com",
        "adresZamieszkania": {"miejscowosc": "P�ock", "ulica": "Krakowska", "nrDomu": 77}
    }
]);

db.pokoje.insertMany([
    {
        "idPokoju": 100,
        "pietro": 1,
        "liczbaMiejsc": 2,
        "dostawka": "T",
        "cenaZaDobe": 210,
        "cenaZaDostawke": [{"liczbaDostawek": 1, "cenaZaDobe": 50}, {"liczbaDostawek": 2, "cenaZaDobe": 100}]
    },
    {
        "idPokoju": 101,
        "pietro": 1,
        "liczbaMiejsc": 3,
        "dostawka": "T",
        "cenaZaDobe": 320,
        "cenaZaDostawke": [{"liczbaDostawek": 1, "cenaZaDobe": 75}]
    },
    {"idPokoju": 102, "pietro": 1, "liczbaMiejsc": 1, "dostawka": "N", "cenaZaDobe": 110},
    {"idPokoju": 103, "pietro": 1, "liczbaMiejsc": 4, "dostawka": "N", "cenaZaDobe": 420},
    {"idPokoju": 201, "pietro": 2, "liczbaMiejsc": 3, "dostawka": "N", "cenaZaDobe": 400},
    {"idPokoju": 202, "pietro": 2, "liczbaMiejsc": 4, "dostawka": "N", "cenaZaDobe": 510},
    {
        "idPokoju": 203,
        "pietro": 2,
        "liczbaMiejsc": 2,
        "dostawka": "T",
        "cenaZaDobe": 610,
        "cenaZaDostawke": [{"liczbaDostawek": 2, "cenaZaDobe": 150}]
    },
    {
        "idPokoju": 204,
        "pietro": 2,
        "liczbaMiejsc": 1,
        "dostawka": "T",
        "cenaZaDobe": 99,
        "cenaZaDostawke": [{"liczbaDostawek": 1, "cenaZaDobe": 30}]
    },
    {
        "idPokoju": 205,
        "pietro": 2,
        "liczbaMiejsc": 1,
        "dostawka": "T",
        "cenaZaDobe": 130,
        "cenaZaDostawke": [{"liczbaDostawek": 1, "cenaZaDobe": 80}]
    }
]);

db.pokoje.createIndex({cenaZaDobe: 1}, {name: "idxCenaZaDobe"});

db.zakwaterowanie.insertMany([
    {
        "idZakwaterowania": 1,
        "idPokoju": 100,
        "idKlienta": 1,
        "dataZakwaterowania": ISODate("2024-01-01T15:34:23Z"),
        "dataWykwaterowania": ISODate("2024-01-10T12:33:02Z"),
        "koszt": 1259.33
    },
    {
        "idZakwaterowania": 2,
        "idPokoju": 102,
        "idKlienta": 2,
        "dataZakwaterowania": ISODate("2024-02-12T21:22:55Z"),
        "dataWykwaterowania": ISODate("2024-02-13T09:00:22Z"),
        "koszt": 200
    },
    {"idZakwaterowania": 3, "idPokoju": 204, "idKlienta": 3, "dataZakwaterowania": ISODate("2024-02-23T17:13:03Z")},
    {"idZakwaterowania": 4, "idPokoju": 100, "idKlienta": 4, "dataZakwaterowania": ISODate("2024-03-10T15:17:58Z")},
    {
        "idZakwaterowania": 5,
        "idPokoju": 102,
        "idKlienta": 2,
        "dataZakwaterowania": ISODate("2024-03-01T00:22:55Z"),
        "dataWykwaterowania": ISODate("2024-03-10T09:23:00Z"),
        "koszt": 1250
    }
]);
