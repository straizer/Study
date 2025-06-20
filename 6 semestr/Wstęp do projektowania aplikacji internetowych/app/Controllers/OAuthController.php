<?php
declare(strict_types=1);

namespace App\Controllers;

use App\Database;
use App\Models\Auth;
use Exception;

class OAuthController extends Controller
{
	private const GOOGLE_CLIENT_ID = '687047111726-e4313p8cehpfjqbasenm1k6qvqrbrjts.apps.googleusercontent.com';
	private const GOOGLE_CLIENT_SECRET = '';
	private const GOOGLE_REDIRECT_URI = 'http://localhost:8080/oauth/google/callback';
	private const GOOGLE_AUTH_URL = 'https://accounts.google.com/o/oauth2/v2/auth';
	private const GOOGLE_TOKEN_URL = 'https://oauth2.googleapis.com/token';
	private const GOOGLE_SCOPE = 'https://www.googleapis.com/auth/fitness.activity.read';

	public function index(): void
	{
		header('Location: /');
		exit;
	}

	public function googleAuth(): void
	{
		try {
			Auth::requireAuth();
			$state = bin2hex(random_bytes(16));

			setcookie('oauth_state', $state, [
				'expires' => time() + 600, // 10 minutes
				'path' => '/',
				'httponly' => true,
				'samesite' => 'Lax',
			]);

			$authUrl = self::GOOGLE_AUTH_URL . '?' . http_build_query([
					'client_id' => self::GOOGLE_CLIENT_ID,
					'redirect_uri' => self::GOOGLE_REDIRECT_URI,
					'response_type' => 'code',
					'scope' => self::GOOGLE_SCOPE,
					'access_type' => 'offline',
					'prompt' => 'consent',
					'state' => $state,
				]);

			header('Location: ' . $authUrl);
			exit;
		} catch (Exception) {
			header('Location: /?error=oauth_init_failed');
			exit;
		}
	}

	public function googleCallback(): void
	{
		try {
			if (!isset($_COOKIE['session_token'])) {
				header('Location: /login');
				exit;
			}

			try {
				$user = Auth::user();
				if ($user === null) {
					header('Location: /login');
					exit;
				}
			} catch (Exception) {
				header('Location: /login');
				exit;
			}

			try {
				if (!isset($_GET['state']) || !isset($_COOKIE['oauth_state']) || $_GET['state'] !== $_COOKIE['oauth_state']) {
					setcookie('oauth_state', '', [
						'expires' => time() - 3600,
						'path' => '/',
						'httponly' => true,
						'samesite' => 'Lax',
					]);

					header('Location: /?error=oauth_state_mismatch');
					exit;
				}

				setcookie('oauth_state', '', [
					'expires' => time() - 3600,
					'path' => '/',
					'httponly' => true,
					'samesite' => 'Lax',
				]);
			} catch (Exception $exception) {
				header('Location: /?error=state_verification_failed&message=' . urlencode($exception->getMessage()));
				exit;
			}

			try {
				if (!isset($_GET['code'])) {
					header('Location: /?error=oauth_failed');
					exit;
				}
				$code = $_GET['code'];
			} catch (Exception $exception) {
				header('Location: /?error=code_check_failed&message=' . urlencode($exception->getMessage()));
				exit;
			}

			try {
				$tokenData = $this->exchangeCodeForToken($code);
				if (!$tokenData) {
					header('Location: /?error=token_exchange_failed');
					exit;
				}
				$storeResult = $this->storeTokens($user['id'], 'google', $tokenData);
				if (!$storeResult) {
					error_log('Failed to store tokens in the database');
					header('Location: /?error=token_storage_failed');
					exit;
				}

			} catch (Exception $exception) {
				header('Location: /?error=token_processing_failed&message=' . urlencode($exception->getMessage()));
				exit;
			}

			try {
				header('Location: /?success=google_connected');
				exit;
			} catch (Exception) {
				echo '<script>window.location.href = "/?success=google_connected&fallback=true";</script>';
				echo 'If you are not redirected automatically, please <a href="/?success=google_connected&fallback=true">click here</a>.';
				exit;
			}
		} catch (Exception $exception) {
			try {
				header('Location: /?error=oauth_callback_failed&message=' . urlencode($exception->getMessage()));
				exit;
			} catch (Exception) {
				echo '<html><head><title>Authentication Error</title></head><body>';
				echo '<h1>Authentication Error</h1>';
				echo '<p>There was an error processing your authentication request. Please try again.</p>';
				echo '<p>Error details: ' . htmlspecialchars($exception->getMessage()) . '</p>';
				echo '<p><a href="/">Return to Home Page</a></p>';
				echo '</body></html>';
				exit;
			}
		}
	}

	private function exchangeCodeForToken(string $code): ?array
	{
		$ch = curl_init(self::GOOGLE_TOKEN_URL);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_POST, true);

		$postFields = [
			'code' => $code,
			'client_id' => self::GOOGLE_CLIENT_ID,
			'client_secret' => self::GOOGLE_CLIENT_SECRET,
			'redirect_uri' => self::GOOGLE_REDIRECT_URI,
			'grant_type' => 'authorization_code',
		];

		curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postFields));
		curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/x-www-form-urlencoded']);

		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, true);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 2);

		curl_setopt($ch, CURLOPT_TIMEOUT, 30);

		curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
		curl_setopt($ch, CURLOPT_MAXREDIRS, 5);

		$verbose = fopen('php://temp', 'w+');
		curl_setopt($ch, CURLOPT_VERBOSE, true);
		curl_setopt($ch, CURLOPT_STDERR, $verbose);

		$response = curl_exec($ch);
		$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

		rewind($verbose);
		fclose($verbose);

		curl_close($ch);

		if ($httpCode !== 200) {
			return null;
		}

		$tokenData = json_decode($response, true);

		if (!$tokenData) {
			return null;
		}

		return $tokenData;
	}

	private function storeTokens(string $userId, string $provider, array $tokenData): bool
	{
		try {
			$query = 'SELECT id FROM oauth_tokens WHERE user_id = :user_id AND provider = :provider';
			$result = Database::query($query, [
				':user_id' => $userId,
				':provider' => $provider,
			]);

			if (empty($result)) {
				$query = 'INSERT INTO oauth_tokens (user_id, provider, access_token, refresh_token, expires_at) 
                          VALUES (:user_id, :provider, :access_token, :refresh_token, :expires_at)';
			} else {
				$query = 'UPDATE oauth_tokens 
                          SET access_token = :access_token, refresh_token = :refresh_token, expires_at = :expires_at 
                          WHERE user_id = :user_id AND provider = :provider';
			}

			$expiresAt = date('Y-m-d H:i:s', time() + $tokenData['expires_in']);

			$params = [
				':user_id' => $userId,
				':provider' => $provider,
				':access_token' => $tokenData['access_token'],
				':refresh_token' => $tokenData['refresh_token'] ?? null,
				':expires_at' => $expiresAt,
			];
			Database::execute($query, $params);

			return true;
		} catch (Exception) {
			return false;
		}
	}
}
