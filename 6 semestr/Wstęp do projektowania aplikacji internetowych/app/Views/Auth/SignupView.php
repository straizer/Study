<?php
declare(strict_types=1);

?>
<div>
	<img class="logo" src="/public/assets/logo.svg" alt="HealthSync Logo">
	<h1>Welcome In</h1>
	<p class="subtitle">Sign up to start with HealthSync</p>
	<form>
		<div class="form-group">
			<label for="email">Email</label>
			<input id="email" placeholder="name@example.com" type="email">
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input id="password" placeholder="••••••••" type="password">
		</div>
		<div class="form-group">
			<label for="repeat-password">Repeat password</label>
			<input id="repeat-password" placeholder="••••••••" type="password">
		</div>
		<div class="form-group">
			<button type="submit"><i class="fa-solid fa-right-to-bracket"></i> Sign up</button>
		</div>
	</form>
	<p class="signup-text">Already have an account? <a href="/login">Login</a></p>
</div>
