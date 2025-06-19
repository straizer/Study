package http

import (
	"fmt"
	"time"

	"to/internal/domain/model"
	"to/pkg/utils"
)

type ReservationHttpInterface struct {
	httpInterface[*model.Reservation, reservationDTO]
}

type reservationDTO struct {
	ID          string   `json:"id"`
	RoomID      string   `json:"room_id"`
	OrganizerID string   `json:"organizer_id"`
	InviteeIDs  []string `json:"invitee_ids"`
	Title       string   `json:"title"`
	Description string   `json:"description"`
	StartTime   string   `json:"start_time"`
	EndTime     string   `json:"end_time"`
}

func NewReservationHttpInterface(usecase usecase[*model.Reservation]) *ReservationHttpInterface {
	return &ReservationHttpInterface{
		httpInterface[*model.Reservation, reservationDTO]{
			usecase: usecase,
			dtoToEntity: func(dto reservationDTO) (*model.Reservation, error) {
				startTime, err := time.Parse(time.RFC3339, dto.StartTime)
				if err != nil {
					return nil, getInvalidFormatError("startTime time", dto.StartTime, time.RFC3339, err)
				}
				endTime, err := time.Parse(time.RFC3339, dto.EndTime)
				if err != nil {
					return nil, getInvalidFormatError("end time", dto.StartTime, time.RFC3339, err)
				}
				return utils.Ptr(model.NewReservation(
					dto.ID, dto.RoomID, dto.OrganizerID, dto.Title, dto.Description, dto.InviteeIDs, startTime, endTime,
				)), nil
			},
			entityToDto: func(entity *model.Reservation) reservationDTO {
				return reservationDTO{
					entity.ID, entity.RoomID, entity.OrganizerID, entity.InviteeIDs, entity.Title,
					entity.Description, entity.StartTime.Format(time.RFC3339), entity.EndTime.Format(time.RFC3339),
				}
			},
		},
	}
}

func getInvalidFormatError(propertyName, actual, expected string, err error) error {
	return fmt.Errorf("invalid %s format <%s>, expected <%s>: %w", propertyName, actual, expected, err)
}
