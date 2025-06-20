<?php
declare(strict_types=1);

require_once __DIR__ . '/autoload.php';

use App\Router;

$router = new Router();

$router->get('/', function() {
	echo 'Home Page';
});

$router->get('/login', [App\Controllers\Auth\LoginController::class, 'index']);
$router->get('/signup', [App\Controllers\Auth\SignupController::class, 'index']);
$router->get('/test-db', [App\Controllers\TestController::class, 'index']);

$router->run();
