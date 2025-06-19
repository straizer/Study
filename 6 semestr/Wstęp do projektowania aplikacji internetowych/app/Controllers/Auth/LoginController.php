<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;

class LoginController extends Controller
{
	public function index(): void
	{
		$this->render('login');
	}
}