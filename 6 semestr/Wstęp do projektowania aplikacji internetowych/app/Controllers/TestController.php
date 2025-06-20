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
			$connection = Database::getConnection();
			$result = ['status' => 'success', 'message' => 'Database connection successful'];

			$query = 'SELECT current_database() as db_name, current_user as user_name';
			$data = Database::query($query);
			$result['data'] = $data;

		} catch (PDOException $exception) {
			$result = ['status' => 'error', 'message' => 'Database connection failed: ' . $exception->getMessage()];
		}

		// Display the result
		echo '<pre>';
		print_r($result);
		echo '</pre>';
	}
}