<?php
declare(strict_types=1);

namespace App\Models;

use App\Database;
use PDOException;
use Random\RandomException;
use RuntimeException;

class Session
{
	private static int $expirationMinutes = 10;

	public static function create(string $userId): string
	{
		try {
			$token = bin2hex(random_bytes(32));
			$expires_at = date('Y-m-d H:i:s', time() + (self::$expirationMinutes * 60));

			$query = 'INSERT INTO sessions (user_id, token, expires_at) VALUES (:user_id, :token, :expires_at)';
			Database::execute($query, [
				':user_id' => $userId,
				':token' => $token,
				':expires_at' => $expires_at,
			]);

			setcookie('session_token', $token, [
				'expires' => time() + (self::$expirationMinutes * 60),
				'path' => '/',
				'httponly' => true,
				'samesite' => 'Lax',
			]);

			return $token;
		} catch (PDOException|RandomException $exception) {
			throw new RuntimeException('Failed to create session: ' . $exception->getMessage());
		}
	}

	public static function logout(?string $token = null): bool
	{
		if ($token === null) {
			if (!isset($_COOKIE['session_token'])) {
				return false;
			}
			$token = $_COOKIE['session_token'];
		}

		try {
			$query = 'UPDATE sessions SET expires_at = NOW() WHERE token = :token';
			Database::execute($query, [':token' => $token]);

			self::invalidateCookie();

			return true;
		} catch (PDOException) {
			return false;
		}
	}

	private static function invalidateCookie(): void
	{
		setcookie('session_token', '', [
			'expires' => time() - 3600,
			'path' => '/',
			'httponly' => true,
			'samesite' => 'Lax', // Changed from Strict to Lax to be consistent with the session cookie
		]);
	}

	public static function getUser(): ?array
	{
		return self::validate();
	}

	public static function validate(): ?array
	{
		if (!isset($_COOKIE['session_token'])) {
			return null;
		}

		$token = $_COOKIE['session_token'];

		try {
			$query = 'SELECT s.*, u.* FROM sessions s 
                      JOIN users u ON s.user_id = u.id 
                      WHERE s.token = :token AND s.expires_at > NOW()';
			$result = Database::query($query, [':token' => $token]);

			if (empty($result)) {
				self::invalidateCookie();
				return null;
			}

			return $result[0];
		} catch (PDOException) {
			self::invalidateCookie();
			return null;
		}
	}
}
