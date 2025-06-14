package observer

type ReservationEventType string

const (
	ReservationCreated   ReservationEventType = "created"
	ReservationCancelled ReservationEventType = "cancelled"
)

type Observer interface {
	Notify(*ReservationEvent)
	GetID() string
}

type Subject interface {
	Register(Observer)
	Deregister(Observer)
	NotifyAll(ReservationEventType)
}

type ReservationEvent struct {
	Title string
	Type  ReservationEventType
}
