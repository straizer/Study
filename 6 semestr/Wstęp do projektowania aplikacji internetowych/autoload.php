<?php
declare(strict_types=1);

spl_autoload_register(function ($class) {
	$prefix = 'App\\';
	$base_directory = __DIR__ . '/app/';

	$length = strlen($prefix);
	if (strncmp($prefix, $class, $length) !== 0) {
		return;
	}

	$relative_class = substr($class, $length);
	$file = $base_directory . str_replace('\\', '/', $relative_class) . '.php';

	if (file_exists($file)) require $file;
});