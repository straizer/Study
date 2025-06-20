<?php
declare(strict_types=1);

namespace App\Controllers\Auth;

use App\Controllers\Controller;
use App\Database;
use App\Models\Auth;
use App\Models\Session;
use RuntimeException;

class SignupController extends Controller
{
	public function index(): void
	{
		Auth::requireGuest();
		$this->render('Sign up', 'Auth/SignupView');
	}

	public function register(): void
	{
		$email = $_POST['email'] ?? '';
		$password = $_POST['password'] ?? '';
		$repeat_password = $_POST['repeat_password'] ?? '';

		$errors = [];

		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
			$errors[] = 'Invalid email format';
		}

		if (empty($password) || empty($repeat_password)) {
			$errors[] = 'Both password fields must be filled';
		} else {
			if ($password !== $repeat_password) {
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

		$hashed_password = password_hash($password, PASSWORD_DEFAULT);

		$insert_query = 'INSERT INTO users (email, password) VALUES (:email, :password) RETURNING id';
		$result = Database::query($insert_query, [
			':email' => $email,
			':password' => $hashed_password,
		]);

		if (empty($result)) {
			$this->render('Sign up', 'Auth/SignupView', [
				'errors' => ['Registration failed: Could not create user'],
				'email' => $email,
			]);
			return;
		}

		$user_id = $result[0]['id'];

		try {
			Session::create($user_id);
			Auth::requireGuest();
		} catch (RuntimeException $exception) {
			$this->render('Sign up', 'Auth/SignupView', [
				'errors' => ['Registration completed but login failed: ' . $exception->getMessage()],
				'email' => $email,
			]);
			return;
		}
	}
}
