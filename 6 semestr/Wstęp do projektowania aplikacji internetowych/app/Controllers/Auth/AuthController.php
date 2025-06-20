<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;
use App\Models\Auth;

abstract class AuthController extends Controller
{
	public function index(): void
	{
		Auth::requireGuest();
	}
}