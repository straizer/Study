<?php
declare(strict_types=1);

namespace App;

use PDO;
use PDOException;
use RuntimeException;

class Database
{
	private static ?PDO $connection = null;
	private static string $host = 'db';
	private static string $port = '5432';
	private static string $dbname = 'postgres';
	private static string $username = 'postgres';
	private static string $password = 'password';

	public static function query(string $query, array $params = []): array
	{
		try {
			$stmt = self::getConnection()->prepare($query);
			$stmt->execute($params);
			return $stmt->fetchAll();
		} catch (PDOException $e) {
			throw new RuntimeException('Query failed: ' . $e->getMessage());
		}
	}

	public static function getConnection(): PDO
	{
		if (self::$connection === null) {
			try {
				$dsn = 'pgsql:host=' . self::$host . ';port=' . self::$port . ';dbname=' . self::$dbname;
				self::$connection = new PDO(
					$dsn,
					self::$username,
					self::$password,
					[
						PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
						PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
						PDO::ATTR_EMULATE_PREPARES => false,
					],
				);
			} catch (PDOException $e) {
				throw new RuntimeException('Database connection failed: ' . $e->getMessage());
			}
		}

		return self::$connection;
	}

	public static function execute(string $query, array $params = []): int
	{
		try {
			$stmt = self::getConnection()->prepare($query);
			$stmt->execute($params);
			return $stmt->rowCount();
		} catch (PDOException $e) {
			throw new RuntimeException('Query failed: ' . $e->getMessage());
		}
	}

	public static function lastInsertId(): string
	{
		return self::getConnection()->lastInsertId();
	}
}