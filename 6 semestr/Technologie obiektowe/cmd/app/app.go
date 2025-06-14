package app

import (
	"strconv"

	"github.com/gin-gonic/gin"
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
	router := gin.Default()

	router.POST("/reservations", a.deps.reservationHandler.Add)
	router.GET("/reservations", a.deps.reservationHandler.List)
	router.GET("/reservations/:id", a.deps.reservationHandler.Get)
	router.DELETE("/reservations/:id", a.deps.reservationHandler.Remove)

	router.POST("/rooms", a.deps.roomHandler.Add)
	router.GET("/rooms", a.deps.roomHandler.List)
	router.GET("/rooms/:id", a.deps.roomHandler.Get)
	router.DELETE("/rooms/:id", a.deps.roomHandler.Remove)

	router.POST("/users", a.deps.userHandler.Add)
	router.GET("/users", a.deps.userHandler.List)
	router.GET("/users/:id", a.deps.userHandler.Get)
	router.DELETE("/users/:id", a.deps.userHandler.Remove)

	_ = router.Run(":" + strconv.Itoa(a.cfg.Port))
}
