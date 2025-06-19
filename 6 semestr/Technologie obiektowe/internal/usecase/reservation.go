package usecase

import (
	"fmt"
	"time"

	"to/internal/domain/model"
	"to/internal/domain/observer"
)

type reservationUsecase struct {
	usecase[*model.Reservation]
	userUsecase *userUsecase
	roomUsecase *roomUsecase
}

func NewReservationUsecase(
	repository repository[*model.Reservation], userUsecase *userUsecase, roomUsecase *roomUsecase,
) *reservationUsecase {
	return &reservationUsecase{usecase[*model.Reservation]{repository}, userUsecase, roomUsecase}
}

func (s *reservationUsecase) Add(reservation *model.Reservation) error {
	if reservation.StartTime.After(reservation.EndTime) {
		return fmt.Errorf("start time must be before end time")
	}
	if reservation.StartTime.Before(time.Now()) {
		return fmt.Errorf("start time must be in the future")
	}
	_, err := s.roomUsecase.Get(reservation.RoomID)
	if err != nil {
		return fmt.Errorf("room not found: %w", err)
	}
	organizer, err := s.userUsecase.Get(reservation.OrganizerID)
	if err != nil {
		return fmt.Errorf("organizer not found: %w", err)
	}
	reservation.Register(*organizer)
	for _, inviteeID := range reservation.InviteeIDs {
		invitee, err := s.userUsecase.Get(inviteeID)
		if err != nil {
			return fmt.Errorf("invitee not found: %w", err)
		}
		reservation.Register(*invitee)
	}
	if err = s.usecase.Add(reservation); err != nil {
		return fmt.Errorf("failed to add reservation: %w", err)
	}
	reservation.NotifyAll(observer.ReservationCreated)
	return nil
}

func (s *reservationUsecase) Remove(id string) (**model.Reservation, error) {
	reservation, err := s.usecase.Remove(id)
	if err != nil {
		return nil, fmt.Errorf("reservation not found: %w", err)
	}
	(*reservation).NotifyAll(observer.ReservationCancelled)
	return reservation, nil
}
