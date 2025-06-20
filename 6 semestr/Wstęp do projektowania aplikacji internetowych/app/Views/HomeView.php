<?php
declare(strict_types=1);

$user = $user ?? null;
?>

<div class="home-content">
	<h1>Welcome back, <?php echo htmlspecialchars($user['email']); ?></h1>
	<p class="health-overview">Here's your health overview for today</p>
</div>
