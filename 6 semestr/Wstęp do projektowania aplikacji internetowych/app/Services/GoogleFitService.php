<?php
declare(strict_types=1);

namespace App\Services;

use App\Database;
use App\Models\Auth;
use Exception;

class GoogleFitService
{
    private const GOOGLE_FITNESS_API_URL = 'https://www.googleapis.com/fitness/v1/users/me/dataSources/';
    private const STEPS_DATA_SOURCE = 'derived:com.google.step_count.delta:com.google.android.gms:estimated_steps';
    private const HEART_RATE_DATA_SOURCE = 'derived:com.google.heart_rate.bpm:com.google.android.gms:merge_heart_rate_bpm';
    private const CALORIES_DATA_SOURCE = 'derived:com.google.calories.expended:com.google.android.gms:merge_calories_expended';
    private const SLEEP_DATA_SOURCE = 'derived:com.google.sleep.segment:com.google.android.gms:merged';

    /**
     * Get the access token for the current user
     *
     * @return string|null The access token or null if not found
     */
    private function getAccessToken(): ?string
    {
        try {
            $user = Auth::user();
            if ($user === null) {
                return null;
            }

            $query = 'SELECT access_token, expires_at FROM oauth_tokens 
                      WHERE user_id = :user_id AND provider = :provider AND expires_at > NOW()';
            $result = Database::query($query, [
                ':user_id' => $user['id'],
                ':provider' => 'google',
            ]);

            if (empty($result)) {
                return null;
            }

            return $result[0]['access_token'];
        } catch (Exception $e) {
            error_log('Failed to get access token: ' . $e->getMessage());
            return null;
        }
    }

    /**
     * Make an API call to the Google Fitness API
     *
     * @param string $endpoint The API endpoint
     * @param array $params The query parameters
     * @return array|null The response data or null if the call failed
     */
    private function makeApiCall(string $endpoint, array $params = []): ?array
    {
        $accessToken = $this->getAccessToken();
        if ($accessToken === null) {
            return null;
        }

        $url = self::GOOGLE_FITNESS_API_URL . $endpoint;
        if (!empty($params)) {
            $url .= '?' . http_build_query($params);
        }

        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Authorization: Bearer ' . $accessToken,
            'Content-Type: application/json',
        ]);

        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        if ($httpCode !== 200) {
            error_log('Google Fitness API call failed with HTTP code: ' . $httpCode);
            error_log('Response: ' . $response);
            return null;
        }

        return json_decode($response, true);
    }

    /**
     * Get the step count for today
     *
     * @return string The step count formatted as a string
     */
    public function getStepsToday(): string
    {
        try {
            $startTime = strtotime('today midnight');
            $endTime = time();

            $params = [
                'startTimeMillis' => $startTime * 1000,
                'endTimeMillis' => $endTime * 1000,
            ];

            $response = $this->makeApiCall(self::STEPS_DATA_SOURCE . '/datasets/' . ($startTime * 1000000000) . '-' . ($endTime * 1000000000), $params);

            if ($response === null || !isset($response['point'])) {
                return '0';
            }

            $totalSteps = 0;
            foreach ($response['point'] as $point) {
                if (isset($point['value'][0]['intVal'])) {
                    $totalSteps += $point['value'][0]['intVal'];
                }
            }

            return number_format($totalSteps);
        } catch (Exception $e) {
            error_log('Failed to get steps: ' . $e->getMessage());
            return '0';
        }
    }

    /**
     * Get the heart rate
     *
     * @return string The heart rate formatted as a string
     */
    public function getHeartRate(): string
    {
        try {
            $startTime = strtotime('-1 hour');
            $endTime = time();

            $params = [
                'startTimeMillis' => $startTime * 1000,
                'endTimeMillis' => $endTime * 1000,
            ];

            $response = $this->makeApiCall(self::HEART_RATE_DATA_SOURCE . '/datasets/' . ($startTime * 1000000000) . '-' . ($endTime * 1000000000), $params);

            if ($response === null || !isset($response['point']) || empty($response['point'])) {
                return '0 BPM';
            }

            // Get the most recent heart rate reading
            $latestPoint = end($response['point']);
            $heartRate = $latestPoint['value'][0]['fpVal'] ?? 0;

            return round($heartRate) . ' BPM';
        } catch (Exception $e) {
            error_log('Failed to get heart rate: ' . $e->getMessage());
            return '0 BPM';
        }
    }

    /**
     * Get the sleep duration
     *
     * @return string The sleep duration formatted as a string
     */
    public function getSleepDuration(): string
    {
        try {
            $startTime = strtotime('yesterday midnight');
            $endTime = strtotime('today midnight');

            $params = [
                'startTimeMillis' => $startTime * 1000,
                'endTimeMillis' => $endTime * 1000,
            ];

            $response = $this->makeApiCall(self::SLEEP_DATA_SOURCE . '/datasets/' . ($startTime * 1000000000) . '-' . ($endTime * 1000000000), $params);

            if ($response === null || !isset($response['point']) || empty($response['point'])) {
                return '0h 0min';
            }

            $totalSleepMillis = 0;
            foreach ($response['point'] as $point) {
                if (isset($point['value'][0]['intVal']) && $point['value'][0]['intVal'] === 1) { // 1 = sleep
                    $startTimeNanos = $point['startTimeNanos'];
                    $endTimeNanos = $point['endTimeNanos'];
                    $durationMillis = ($endTimeNanos - $startTimeNanos) / 1000000;
                    $totalSleepMillis += $durationMillis;
                }
            }

            $hours = floor($totalSleepMillis / (60 * 60 * 1000));
            $minutes = floor(($totalSleepMillis % (60 * 60 * 1000)) / (60 * 1000));

            return $hours . 'h ' . $minutes . 'min';
        } catch (Exception $e) {
            error_log('Failed to get sleep duration: ' . $e->getMessage());
            return '0h 0min';
        }
    }

    /**
     * Get the calories burnt
     *
     * @return string The calories burnt formatted as a string
     */
    public function getCaloriesBurnt(): string
    {
        try {
            $startTime = strtotime('today midnight');
            $endTime = time();

            $params = [
                'startTimeMillis' => $startTime * 1000,
                'endTimeMillis' => $endTime * 1000,
            ];

            $response = $this->makeApiCall(self::CALORIES_DATA_SOURCE . '/datasets/' . ($startTime * 1000000000) . '-' . ($endTime * 1000000000), $params);

            if ($response === null || !isset($response['point'])) {
                return '0';
            }

            $totalCalories = 0;
            foreach ($response['point'] as $point) {
                if (isset($point['value'][0]['fpVal'])) {
                    $totalCalories += $point['value'][0]['fpVal'];
                }
            }

            return number_format(round($totalCalories));
        } catch (Exception $e) {
            error_log('Failed to get calories burnt: ' . $e->getMessage());
            return '0';
        }
    }

    /**
     * Check if the user has connected to Google Fit
     *
     * @return bool True if connected, false otherwise
     */
    public function isConnected(): bool
    {
        return $this->getAccessToken() !== null;
    }
}
