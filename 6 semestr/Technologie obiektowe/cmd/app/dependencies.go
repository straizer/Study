package app

import (
	"to/internal/infrastructure/repository"
	"to/internal/interface/http"
	"to/internal/usecase"
)

type dependencies struct {
	userHttpInterface        *http.UserHttpInterface
	roomHttpInterface        *http.RoomHttpInterface
	reservationHttpInterface *http.ReservationHttpInterface
}

func NewDependencies(_ *Options) *dependencies {
	userRepository := repository.NewUserRepository()
	roomRepository := repository.NewRoomRepository()
	reservationRepository := repository.NewReservationRepository()

	userUsecase := usecase.NewUserUsecase(userRepository)
	roomUsecase := usecase.NewRoomUsecase(roomRepository)
	reservationUsecase := usecase.NewReservationUsecase(reservationRepository, userUsecase, roomUsecase)

	userHttpInterface := http.NewUserHttpInterface(userUsecase)
	roomHttpInterface := http.NewRoomHttpInterface(roomUsecase)
	reservationHttpInterface := http.NewReservationHttpInterface(reservationUsecase)

	return &dependencies{userHttpInterface, roomHttpInterface, reservationHttpInterface}
}
