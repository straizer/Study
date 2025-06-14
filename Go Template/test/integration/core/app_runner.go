//go:build integration

package core

import (
	"time"

	"go.uber.org/zap"

	"to/cmd/app"
	"to/pkg/logger"
)

func StartApp() *app.App {
	opts := getOptions()
	deps := app.NewDependencies(opts)
	logger.Initialize(opts.Domain)

	application := app.NewApp(opts, deps)
	go application.Run()

	waitForStart()
	return application
}

func StopApp(_ *app.App) {
	zap.L().Info("stopping the app")
	// add actual stopping if necessary
	zap.L().Info("the app stopped")
}

func waitForStart() {
	started := make(chan bool)

	go func() {
		for {
			time.Sleep(time.Second)
			zap.L().Info("checking if the app started")
			if _, err := healthCheck(); err == nil {
				started <- true
				return
			}
		}
	}()

	select {
	case <-started:
		zap.L().Info("the app started")
	case <-time.After(time.Second * 10):
		zap.L().Panic("the app failed to start in 10 seconds")
	}
}

func healthCheck() (any, error) {
	// replace it with actual check
	return nil, nil
}
