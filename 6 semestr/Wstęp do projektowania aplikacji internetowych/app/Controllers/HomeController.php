<?php
declare(strict_types=1);

namespace App\Controllers;

use App\Models\Auth;

class HomeController extends Controller
{
	public function index(): void
	{
		Auth::requireAuth();
		$user = Auth::user();
		$this->render('Home', 'HomeView', ['user' => $user]);
	}
}