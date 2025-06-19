<?php
declare(strict_types=1);

require_once __DIR__ . '/autoload.php';

use App\Router;

$router = new Router();

// Define routes
$router->get('/', function() {
    echo 'Home Page';
});

$router->get('/login', [App\Controllers\Auth\LoginController::class, 'index']);

// Run the router
$router->run();
