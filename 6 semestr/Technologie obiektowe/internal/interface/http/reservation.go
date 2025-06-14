package http

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"

	"to/internal/domain/model"
)

type reservationUsecase interface {
	Add(*model.Reservation) error
	Get(string) (**model.Reservation, error)
	List() []*model.Reservation
	Remove(string) (**model.Reservation, error)
}

type ReservationHandler struct {
	usecase reservationUsecase
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

func NewReservationHandler(usecase reservationUsecase) *ReservationHandler {
	return &ReservationHandler{usecase}
}

func (h *ReservationHandler) Add(c *gin.Context) {
	var req reservationDTO
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	start, _ := time.Parse(time.RFC3339, req.StartTime)
	end, _ := time.Parse(time.RFC3339, req.EndTime)
	reservation := model.NewReservation(
		req.ID, req.RoomID, req.OrganizerID, req.Title, req.Description, req.InviteeIDs, start, end)
	if err := h.usecase.Add(&reservation); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	c.Status(http.StatusCreated)
}

func (h *ReservationHandler) Get(c *gin.Context) {
	id := c.Param("id")
	reservation, err := h.usecase.Get(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := reservationDTO{
		ID:          (*reservation).ID,
		RoomID:      (*reservation).RoomID,
		OrganizerID: (*reservation).OrganizerID,
		Title:       (*reservation).Title,
		Description: (*reservation).Description,
		InviteeIDs:  (*reservation).InviteeIDs,
		StartTime:   (*reservation).StartTime.Format(time.RFC3339),
		EndTime:     (*reservation).EndTime.Format(time.RFC3339),
	}
	c.JSON(http.StatusOK, response)
}

func (h *ReservationHandler) List(c *gin.Context) {
	reservations := h.usecase.List()
	response := make([]reservationDTO, len(reservations))
	for i, reservation := range reservations {
		response[i] = reservationDTO{
			ID:          reservation.ID,
			RoomID:      reservation.RoomID,
			OrganizerID: reservation.OrganizerID,
			Title:       reservation.Title,
			Description: reservation.Description,
			InviteeIDs:  reservation.InviteeIDs,
			StartTime:   reservation.StartTime.Format(time.RFC3339),
			EndTime:     reservation.EndTime.Format(time.RFC3339),
		}
	}
	c.JSON(http.StatusOK, response)
}

func (h *ReservationHandler) Remove(c *gin.Context) {
	id := c.Param("id")
	reservation, err := h.usecase.Remove(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := reservationDTO{
		ID:          (*reservation).ID,
		RoomID:      (*reservation).RoomID,
		OrganizerID: (*reservation).OrganizerID,
		Title:       (*reservation).Title,
		Description: (*reservation).Description,
		InviteeIDs:  (*reservation).InviteeIDs,
		StartTime:   (*reservation).StartTime.Format(time.RFC3339),
		EndTime:     (*reservation).EndTime.Format(time.RFC3339),
	}
	c.JSON(http.StatusOK, response)
}
