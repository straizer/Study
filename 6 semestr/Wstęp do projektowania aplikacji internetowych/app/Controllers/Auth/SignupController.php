<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;

class SignupController extends Controller
{
	public function index(): void
	{
		$this->render('Sign up', 'Auth/SignupView');
	}
}