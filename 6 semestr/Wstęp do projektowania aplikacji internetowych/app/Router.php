<?php
declare(strict_types=1);

namespace App;

class Router
{
	private array $routes = [];

	public function get(string $url, callable|array $action): void
	{
		$this->addRoute('GET', $url, $action);
	}

	private function addRoute(string $method, string $url, callable|array $action): void
	{
		$this->routes[$method][$url] = $action;
	}

	public function post(string $url, callable|array $action): void
	{
		$this->addRoute('POST', $url, $action);
	}

	public function run(): void
	{
		$method = $_SERVER['REQUEST_METHOD'];
		$url = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

		if (!isset($this->routes[$method][$url])) {
			http_response_code(404);
			echo '404 - Page Not Found';
			return;
		}

		$action = $this->routes[$method][$url];

		if (is_callable($action)) {
			$action();
		} elseif (is_array($action) && count($action) === 2) {
			[$class, $method] = $action;

			if (is_string($class)) {
				$class = new $class();
			}

			$class->$method();
		}
	}
}