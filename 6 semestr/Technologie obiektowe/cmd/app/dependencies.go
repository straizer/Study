package app

import (
	"to/internal/infrastructure/repository"
	"to/internal/interface/http"
	"to/internal/usecase"
)

type dependencies struct {
	roomHandler        *http.RoomHandler
	userHandler        *http.UserHandler
	reservationHandler *http.ReservationHandler
}

func NewDependencies(_ *Options) *dependencies {
	userRepository := repository.NewUserRepository()
	roomRepository := repository.NewRoomRepository()
	reservationRepository := repository.NewReservationRepository()

	userUsecase := usecase.NewUserUsecase(userRepository)
	roomUsecase := usecase.NewRoomUsecase(roomRepository)
	reservationUsecase := usecase.NewReservationUsecase(reservationRepository, userUsecase, roomUsecase)

	userHandler := http.NewUserHandler(userUsecase)
	roomHandler := http.NewRoomHandler(roomUsecase)
	reservationHandler := http.NewReservationHandler(reservationUsecase)

	// return &dependencies{reservationHandler, roomHandler, userHandler}
	return &dependencies{roomHandler, userHandler, reservationHandler}
}
