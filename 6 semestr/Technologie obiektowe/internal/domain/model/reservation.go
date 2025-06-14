package model

import (
	"time"

	"to/internal/domain/observer"
)

type Reservation struct {
	ID, RoomID, Title, Description, OrganizerID string
	InviteeIDs                                  []string
	StartTime, EndTime                          time.Time

	observerList []observer.Observer
}

func NewReservation(
	ID, roomID, organizerID, title, description string, inviteeIDs []string, start, end time.Time,
) Reservation {
	return Reservation{
		ID:           ID,
		RoomID:       roomID,
		Title:        title,
		Description:  description,
		OrganizerID:  organizerID,
		InviteeIDs:   inviteeIDs,
		StartTime:    start,
		EndTime:      end,
		observerList: make([]observer.Observer, 0),
	}
}

func (r *Reservation) GetID() string {
	return r.ID
}

func (r *Reservation) Register(toAdd observer.Observer) {
	r.observerList = append(r.observerList, toAdd)
}

func (r *Reservation) Deregister(toRemove observer.Observer) {
	length := len(r.observerList)
	for i, obs := range r.observerList {
		if toRemove.GetID() == obs.GetID() {
			r.observerList[length-1], r.observerList[i] = r.observerList[i], r.observerList[length-1]
			r.observerList = r.observerList[:length-1]
		}
	}
}

func (r *Reservation) NotifyAll(eventType observer.ReservationEventType) {
	for _, obs := range r.observerList {
		obs.Notify(&observer.ReservationEvent{Title: r.Title, Type: eventType})
	}
}
