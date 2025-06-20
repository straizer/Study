<?php
declare(strict_types=1);

$errors = $errors ?? [];
$email = $email ?? '';
$userExists = $userExists ?? false;
?>
<div>
	<img class="logo" src="/public/assets/logo.svg" alt="HealthSync Logo">
	<h1>Welcome In</h1>
	<p class="subtitle">Sign up to start with HealthSync</p>

	<?php if (!empty($errors)): ?>
		<div class="error-message">
			<?php foreach ($errors as $error): ?>
				<p><?php echo htmlspecialchars($error); ?></p>
			<?php endforeach; ?>
		</div>
	<?php endif; ?>

	<?php if ($userExists): ?>
		<div class="info-message">
			<p>User already has an account. Did you mean to <a href="/login">sign in</a>?</p>
		</div>
	<?php endif; ?>
	<form method="POST" action="/signup/register">
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
			<label for="repeat-password">Repeat password</label>
			<input id="repeat-password" name="repeat_password" placeholder="••••••••" type="password" required>
		</div>
		<div class="form-group">
			<button type="submit"><i class="fa-solid fa-right-to-bracket"></i> Sign up</button>
		</div>
	</form>
	<p class="signup-text">Already have an account? <a href="/login">Sign in</a></p>
</div>
