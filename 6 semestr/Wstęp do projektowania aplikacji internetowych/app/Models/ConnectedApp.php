<?php
declare(strict_types=1);

namespace App\Models;

use App\Database;
use Exception;

class ConnectedApp
{
	private string $name;
	private AppStatus $status;
	private string $icon;
	private string $iconColor;

	public function __construct(string $name, AppStatus $status, string $icon, string $iconColor)
	{
		$this->name = $name;
		$this->status = $status;
		$this->icon = $icon;
		$this->iconColor = $iconColor;
	}

	public static function getAll(): array
	{
		$apps = [
			new self('Fitbit', AppStatus::NotConnected, 'fa-solid fa-ring', '#00b894'),
			new self('Apple Health', AppStatus::NotConnected, 'fa-brands fa-apple', '#636e72'),
			new self('Google Fit', AppStatus::NotConnected, 'fa-brands fa-google', '#4285f4'),
		];

		$user = Auth::user();
		if ($user === null) {
			return $apps;
		}

		$connectedProviders = self::getConnectedProviders($user['id']);

		foreach ($apps as $key => $app) {
			$name = $app->getName();
			$provider = self::appNameToProvider($name);

			if (in_array($provider, $connectedProviders)) {
				$apps[$key] = new self(
					$name,
					AppStatus::Connected,
					$app->getIcon(),
					$app->getIconColor(),
				);
			}
		}

		return $apps;
	}

	private static function getConnectedProviders(string $userId): array
	{
		try {
			$query = 'SELECT provider FROM oauth_tokens WHERE user_id = :user_id AND expires_at > NOW()';
			$result = Database::query($query, [':user_id' => $userId]);

			$providers = [];
			foreach ($result as $row) {
				$providers[] = $row['provider'];
			}

			return $providers;
		} catch (Exception $e) {
			error_log('Failed to get connected providers: ' . $e->getMessage());
			return [];
		}
	}

	public function getName(): string
	{
		return $this->name;
	}

	private static function appNameToProvider(string $appName): string
	{
		$map = [
			'Fitbit' => 'fitbit',
			'Apple Health' => 'apple',
			'Google Fit' => 'google',
		];

		return $map[$appName] ?? strtolower($appName);
	}

	public function getIcon(): string
	{
		return $this->icon;
	}

	public function getIconColor(): string
	{
		return $this->iconColor;
	}

	public function getStatus(): AppStatus
	{
		return $this->status;
	}
}
