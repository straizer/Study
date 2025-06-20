<?php
declare(strict_types=1);

namespace App\Models;

class HealthMetric
{
	private string $name;
	private string $value;
	private string $icon;
	private string $iconColor;

	public function __construct(string $name, string $value, string $icon, string $iconColor)
	{
		$this->name = $name;
		$this->value = $value;
		$this->icon = $icon;
		$this->iconColor = $iconColor;
	}

	public static function getAll(): array
	{
		return [
			new self('Steps Today', '8,439', 'fa-solid fa-shoe-prints', '#0000ff'),
			new self('Hearth Rate', '72 BPM', 'fa-solid fa-heart-pulse', '#ff0000'),
			new self('Sleep', '7h 23 min', 'fa-solid fa-moon', '#800080'),
			new self('Calories Burnt', '1,842', 'fa-solid fa-fire', '#ffa500'),
		];
	}

	public function getName(): string
	{
		return $this->name;
	}

	public function getValue(): string
	{
		return $this->value;
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