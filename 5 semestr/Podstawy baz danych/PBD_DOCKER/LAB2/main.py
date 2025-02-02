import pymysql

# Konfiguracja połączenia
HOST = "db"
PORT = 3306
USER = "root"
PASSWORD = "example"
DATABASE = "lab1"

try:
    connection = pymysql.connect(
        host=HOST,
        port=PORT,
        user=USER,
        password=PASSWORD,
        database=DATABASE,
        cursorclass=pymysql.cursors.DictCursor
    )

    print("✅ Połączono z bazą danych!")

    with connection.cursor() as cursor:
        sql_query = "SELECT * FROM table1;"
        cursor.execute(sql_query)
        results = cursor.fetchall()

        print("\n📌 Dane z tabeli 'table1':")
        for row in results:
            print(row)

except pymysql.MySQLError as e:
    print(f"❌ Błąd połączenia z bazą danych: {e}")

finally:
    if 'connection' in locals() and connection.open:
        connection.close()
        print("🔒 Połączenie zamknięte.")
