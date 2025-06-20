<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;
use App\Database;

class LoginController extends Controller
{
	public function index(): void
	{
		$this->render('Sign in', 'Auth/LoginView');
	}

	public function authenticate(): void
	{
		$email = $_POST['email'] ?? '';
		$password = $_POST['password'] ?? '';

		$errors = [];

		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
			$errors[] = 'Invalid email format';
		}

		if (empty($password)) {
			$errors[] = 'Password field must be filled';
		}

		if (!empty($errors)) {
			$this->render('Sign in', 'Auth/LoginView', ['errors' => $errors, 'email' => $email]);
			return;
		}

		$query = 'SELECT * FROM users WHERE email = :email';
		$result = Database::query($query, [':email' => $email]);

		if (empty($result)) {
			$this->render('Sign in', 'Auth/LoginView', [
				'errors' => ['Invalid email or password'],
				'email' => $email,
			]);
			return;
		}

		$user = $result[0];
		if (!password_verify($password, $user['password'])) {
			$this->render('Sign in', 'Auth/LoginView', [
				'errors' => ['Invalid email or password'],
				'email' => $email,
			]);
			return;
		}

		header('Location: /');
		exit;
	}
}
