<?php
declare(strict_types=1);

$errors = $errors ?? [];
$email = $email ?? '';
?>
<div>
	<img class="logo" src="/public/assets/logo.svg" alt="HealthSync Logo">
	<h1>Welcome Back</h1>
	<p class="subtitle">Sign in to continue to HealthSync</p>

	<?php if (!empty($errors)): ?>
		<div class="error-message">
			<?php foreach ($errors as $error): ?>
				<p><?php echo htmlspecialchars($error); ?></p>
			<?php endforeach; ?>
		</div>
	<?php endif; ?>

	<form method="POST" action="/login/authenticate">
		<div class="form-group">
			<label for="email">Email</label>
			<input id="email" name="email" placeholder="name@example.com" type="email"
			       value="<?php echo htmlspecialchars($email); ?>" required>
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input id="password" name="password" placeholder="••••••••" type="password" required>
		</div>
		<div class="form-group">
			<button type="submit"><i class="fa-solid fa-right-to-bracket"></i> Login</button>
		</div>
	</form>
	<p class="signup-text">Don't have an account? <a href="/signup">Sign up</a></p>
</div>
