package model

type Room struct {
	ID, Name        string
	Capacity, Floor int
}

func NewRoom(ID, name string, capacity, floor int) Room {
	return Room{ID, name, capacity, floor}
}

func (r *Room) GetID() string {
	return r.ID
}
