<?php
declare(strict_types=1);

namespace App\Models;

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
		return [
			new self('Fitbit', AppStatus::NotConnected, 'fa-solid fa-ring', '#00b894'),
			new self('Apple Health', AppStatus::NotConnected, 'fa-brands fa-apple', '#636e72'),
			new self('Google Fit', AppStatus::NotConnected, 'fa-brands fa-google', '#4285f4'),
		];
	}

	public function getName(): string
	{
		return $this->name;
	}

	public function getStatus(): AppStatus
	{
		return $this->status;
	}

	public function getIcon(): string
	{
		return $this->icon;
	}

	public function getIconColor(): string
	{
		return $this->iconColor;
	}
}
