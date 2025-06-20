<?php
declare(strict_types=1);

$user = $user ?? null;
?>

<div class="home-content">
	<h1>Welcome back, <?php echo htmlspecialchars($user['email']); ?></h1>
	<p class="health-overview">Here is your health overview for today</p>

	<div class="health-cards">
		<div class="health-card">
			<p>Steps Today</p>
			<h3>8,439</h3>
			<i class="fa-solid fa-shoe-prints fit-icon" style="color: #0000ff;"></i>
		</div>
		<div class="health-card">
			<p>Hearth Rate</p>
			<h3>72 BPM</h3>
			<i class="fa-solid fa-heart-pulse fit-icon" style="color: #ff0000;"></i>
		</div>
		<div class="health-card">
			<p>Sleep</p>
			<h3>7h 23 min</h3>
			<i class="fa-solid fa-moon fit-icon" style="color: #800080;"></i>
		</div>
		<div class="health-card">
			<p>Calories Burnt</p>
			<h3>1,842</h3>
			<i class="fa-solid fa-fire fit-icon" style="color: #ffa500;"></i>
		</div>
	</div>
</div>
