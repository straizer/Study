<?php
declare(strict_types=1);

require_once __DIR__ . '/autoload.php';

use App\Models\Auth;
use App\Router;

$router = new Router();

$router->get('/', function() {
	Auth::requireAuth();
	$user = Auth::user();
	echo '<h1>Welcome to HealthSync</h1>';
	echo '<p>You are logged in as: ' . htmlspecialchars($user['email']) . '</p>';
	echo '<p><a href="/logout">Logout</a></p>';
});

$router->get('/login', [App\Controllers\Auth\LoginController::class, 'index']);
$router->post('/login/authenticate', [App\Controllers\Auth\LoginController::class, 'authenticate']);
$router->get('/signup', [App\Controllers\Auth\SignupController::class, 'index']);
$router->post('/signup/register', [App\Controllers\Auth\SignupController::class, 'register']);
$router->get('/test-db', [App\Controllers\TestController::class, 'index']);

$router->get('/logout', function() {
	Auth::logout();
	Auth::requireAuth();
});

$router->run();
