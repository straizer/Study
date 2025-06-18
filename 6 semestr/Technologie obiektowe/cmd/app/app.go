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

	router.POST("/users", a.deps.userHttpInterface.Add)
	router.GET("/users", a.deps.userHttpInterface.List)
	router.GET("/users/:id", a.deps.userHttpInterface.Get)
	router.DELETE("/users/:id", a.deps.userHttpInterface.Remove)

	router.POST("/rooms", a.deps.roomHttpInterface.Add)
	router.GET("/rooms", a.deps.roomHttpInterface.List)
	router.GET("/rooms/:id", a.deps.roomHttpInterface.Get)
	router.DELETE("/rooms/:id", a.deps.roomHttpInterface.Remove)

	router.POST("/reservations", a.deps.reservationHttpInterface.Add)
	router.GET("/reservations", a.deps.reservationHttpInterface.List)
	router.GET("/reservations/:id", a.deps.reservationHttpInterface.Get)
	router.DELETE("/reservations/:id", a.deps.reservationHttpInterface.Remove)

	_ = router.Run(":" + strconv.Itoa(a.cfg.Port))
}
