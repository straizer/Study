package main

import (
	"os"
	"os/signal"
	"syscall"

	"go.uber.org/zap"

	"to/cmd/app"
	"to/pkg/logger"
	"to/pkg/options"
)

func main() {
	opts := options.Get[app.Options]()
	deps := app.NewDependencies(opts)
	logger.Initialize(opts.Domain)

	application := app.NewApp(opts, deps)

	go func() {
		defer func() {
			if r := recover(); r != nil {
				zap.L().Panic("exiting due to panic", zap.Any("panic", r))
			}
		}()
		application.Run()
	}()

	exitSignalChannel := make(chan os.Signal, 1)
	signal.Notify(exitSignalChannel, syscall.SIGINT, syscall.SIGTERM)
	exitSignal := <-exitSignalChannel
	zap.L().Info("exiting due to exit signal", zap.String("signal", exitSignal.String()))

}
