<?php
declare(strict_types=1);

namespace App\Controllers;

use App\Views\View;

abstract class Controller
{
	protected function render(string $title, string $view, array $variables = []): void
	{
		$view_object = new View($title, $view);
		$view_object->render($variables);
	}
}
