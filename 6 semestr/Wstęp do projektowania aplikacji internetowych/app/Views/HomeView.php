<?php
declare(strict_types=1);

$user = $user ?? null;
?>

<div>
	<h1>Welcome to HealthSync</h1>
	<p>You are logged in as: <?php echo htmlspecialchars($user['email']); ?></p>
	<p><a href="/logout">Logout</a></p>
</div>