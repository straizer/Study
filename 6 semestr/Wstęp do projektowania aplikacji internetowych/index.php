<?php
declare(strict_types=1);

require_once __DIR__ . '/autoload.php';

use App\Models\Auth;
use App\Router;

$router = new Router();

$router->get('/', [App\Controllers\HomeController::class, 'index']);
$router->get('/test-db', [App\Controllers\TestController::class, 'index']);

$router->get('/login', [App\Controllers\Auth\LoginController::class, 'index']);
$router->post('/login/authenticate', [App\Controllers\Auth\LoginController::class, 'authenticate']);
$router->get('/signup', [App\Controllers\Auth\SignupController::class, 'index']);
$router->post('/signup/register', [App\Controllers\Auth\SignupController::class, 'register']);

$router->get('/logout', function() {
	Auth::logout();
	Auth::requireAuth();
});

$router->run();
