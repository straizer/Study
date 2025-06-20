<?php
declare(strict_types=1);

namespace App\Controllers;

use App\Database;
use PDOException;

class TestController extends Controller
{
	public function index(): void
	{
		try {
			$data = Database::query('SELECT current_database() as db_name, current_user as user_name');
			$result = ['status' => 'success', 'message' => 'Database connection successful', 'data' => $data];
		} catch (PDOException $exception) {
			$result = ['status' => 'error', 'message' => 'Database connection failed: ' . $exception->getMessage()];
		}

		echo '<pre>';
		print_r($result);
		echo '</pre>';
	}
}