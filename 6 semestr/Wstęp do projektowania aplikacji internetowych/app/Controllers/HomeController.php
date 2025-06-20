<?php
declare(strict_types=1);

namespace App\Controllers;

use App\Models\Auth;
use App\Models\ConnectedApp;
use App\Models\HealthMetric;

class HomeController extends Controller
{
	public function index(): void
	{
		Auth::requireAuth();
		$user = Auth::user();
		$health_metrics = HealthMetric::getAll();
		$connected_apps = ConnectedApp::getAll();

		$this->render('Home', 'HomeView', [
			'user' => $user,
			'health_metrics' => $health_metrics,
			'connected_apps' => $connected_apps,
		]);
	}
}
