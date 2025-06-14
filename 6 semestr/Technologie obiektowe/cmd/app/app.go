package app

import (
	"go.uber.org/zap"
)

type App struct {
	cfg  *Options
	deps *dependencies
}

func NewApp(cfg *Options, deps *dependencies) *App {
	return &App{cfg, deps}
}

func (a *App) Run() {
	zap.L().Info("app started")
}
