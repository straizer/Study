<?php
declare(strict_types=1);

namespace App\Views;

class Layout
{
	public static function render(string $content): void
	{
		?>
		<!DOCTYPE html>
		<html lang="en">
		<head>
			<meta charset="UTF-8">
			<meta content="width=device-width, initial-scale=1.0" name="viewport">
			<link href="https://fonts.googleapis.com" rel="preconnect">
			<link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
			<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
			      rel="stylesheet">
			<script crossorigin src="https://kit.fontawesome.com/8fd9367667.js"></script>
			<link href="/public/styles/main.css" rel="stylesheet">
			<title>Login</title>
		</head>
		<body id="login-page">
		<?php echo $content; ?>
		<footer>
			<p>© 2025 HealthSync. All rights reserved.</p>
		</footer>
		</body>
		</html>
		<?php
	}
}
