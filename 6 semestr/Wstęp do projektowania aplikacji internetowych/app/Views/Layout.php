<?php
declare(strict_types=1);

namespace App\Views;

use App\Models\Auth;

class Layout
{
	public static function render(string $title, string $content): void
	{
		$user = Auth::user();
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
			<link rel="icon" href="/public/assets/logo.svg">
			<title>HealthSync - <?php echo $title; ?></title>
		</head>
		<body class="<?php echo strtolower(str_replace(' ', '-', $title)); ?>-page">
		<header>
			<div class="header-content">
				<div class="header-left">
					<img class="logo" src="/public/assets/logo.svg" alt="HealthSync Logo">
					<span>HealthSync</span>
				</div>
				<?php if ($user): ?>
					<div class="header-right">
						<a href="/logout" class="logout-button"><i class="fa-solid fa-sign-out-alt"></i> Logout</a>
					</div>
				<?php endif; ?>
			</div>
		</header>
		<?php echo $content; ?>
		<footer>
			<p>© 2025 HealthSync. All rights reserved.</p>
		</footer>
		<script src="/public/scripts/card-hover.js"></script>
		</body>
		</html>
		<?php
	}
}
