<?php
declare(strict_types=1);

namespace App\Models;

class Auth
{
	public static function user(): ?array
	{
		return Session::getUser();
	}

	public static function requireAuth(): void
	{
		if (!self::check()) {
			header('Location: /login');
			exit;
		}
	}

	private static function check(): bool
	{
		return Session::validate() !== null;
	}

	public static function logout(): bool
	{
		return Session::logout();
	}

	public static function requireGuest(): void
	{
		if (self::check()) {
			header('Location: /');
			exit;
		}
	}
}
