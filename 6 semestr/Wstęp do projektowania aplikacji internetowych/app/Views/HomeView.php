<?php
declare(strict_types=1);

use App\Models\AppStatus;

$user = $user ?? null;
$health_metrics = $health_metrics ?? [];
$connected_apps = $connected_apps ?? [];
?>

<div class="home-content">
	<h1>Welcome back, <?php echo htmlspecialchars($user['email']); ?></h1>
	<p class="health-overview">Here is your health overview for today</p>

	<div class="health-cards">
		<?php foreach ($health_metrics as $metric): ?>
			<div class="health-card">
				<p><?php echo htmlspecialchars($metric->getName()); ?></p>
				<h3><?php echo htmlspecialchars($metric->getValue()); ?></h3>
				<i class="<?php echo htmlspecialchars($metric->getIcon()); ?> fit-icon"
				   style="color: <?php echo htmlspecialchars($metric->getIconColor()); ?>;"></i>
			</div>
		<?php endforeach; ?>
	</div>
	<div class="connect-cards">
		<h3>Connected Apps</h3>
		<div class="connect-cards-container">
			<?php foreach ($connected_apps as $app): ?>
				<button class="connect-card" onclick="window.location.href='<?php echo $app->getName() === 'Google Fit' ? '/oauth/google' : '/'; ?>'">
					<p><?php echo htmlspecialchars($app->getName()); ?></p>
					<h3 class="status-<?php echo $app->getStatus() === AppStatus::Connected ? 'connected' : 'not-connected'; ?>">
						<?php echo htmlspecialchars($app->getStatus()->value); ?>
					</h3>
					<i class="<?php echo htmlspecialchars($app->getIcon()); ?> fit-icon"
					   style="color: <?php echo htmlspecialchars($app->getIconColor()); ?>;"></i>
				</button>
			<?php endforeach; ?>
		</div>
	</div>
</div>
