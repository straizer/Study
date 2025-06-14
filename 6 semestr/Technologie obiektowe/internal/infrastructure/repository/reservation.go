package repository

import (
	"to/internal/domain/model"
)

type ReservationRepository struct {
	repository[*model.Reservation]
}

func NewReservationRepository() *ReservationRepository {
	return &ReservationRepository{*newRepository[*model.Reservation]()}
}
