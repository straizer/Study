<?php
declare(strict_types=1);

namespace App\Models;

enum AppStatus: string
{
	case Connected = 'Connected';
	case NotConnected = 'Not connected';
}