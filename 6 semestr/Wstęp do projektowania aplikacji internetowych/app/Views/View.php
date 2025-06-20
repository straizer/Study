<?php
declare(strict_types=1);

namespace App\Views;

use RuntimeException;

class View
{
	private string $title;
	private string $viewPath;

	public function __construct(string $title, string $view)
	{
		$this->title = $title;
		$this->viewPath = __DIR__ . '/' . $view . '.php';
	}

	public function render(array $variables = []): void
	{
		if (!file_exists($this->viewPath)) {
			throw new RuntimeException("View file not found: {$this->viewPath}");
		}

		extract($variables);

		ob_start();
		include $this->viewPath;
		$content = ob_get_clean();

		Layout::render($this->title, $content);
	}
}
