<?php
declare(strict_types=1);

namespace App\Views;

use RuntimeException;

class View
{
	private string $viewPath;

	public function __construct(string $view)
	{
		$this->viewPath = __DIR__ . '/' . $view . '.php';
	}

	public function render(array $variables = []): void
	{
		if (!file_exists($this->viewPath)) {
			throw new RuntimeException("View file not found: {$this->viewPath}");
		}

		extract($variables);

		include $this->viewPath;
	}
}