<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;
use App\Database;

class SignupController extends Controller
{
	public function index(): void
	{
		$this->render('Sign up', 'Auth/SignupView');
	}

	public function register(): void
	{
		$email = $_POST['email'] ?? '';
		$password = $_POST['password'] ?? '';
		$repeatPassword = $_POST['repeat_password'] ?? '';

		$errors = [];

		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
			$errors[] = 'Invalid email format';
		}

		if (empty($password) || empty($repeatPassword)) {
			$errors[] = 'Both password fields must be filled';
		} else {
			if ($password !== $repeatPassword) {
				$errors[] = 'Passwords do not match';
			}

			if (strlen($password) < 8) {
				$errors[] = 'Password must be at least 8 characters long';
			}
		}

		if (!empty($errors)) {
			$this->render('Sign up', 'Auth/SignupView', ['errors' => $errors, 'email' => $email]);
			return;
		}

		$query = 'SELECT * FROM users WHERE email = :email';
		$result = Database::query($query, [':email' => $email]);

		if (!empty($result)) {
			$this->render('Sign up', 'Auth/SignupView', [
				'userExists' => true,
				'email' => $email,
			]);
			return;
		}

		$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

		$insertQuery = 'INSERT INTO users (email, password) VALUES (:email, :password)';
		Database::execute($insertQuery, [
			':email' => $email,
			':password' => $hashedPassword,
		]);

		header('Location: /');
		exit;
	}
}
