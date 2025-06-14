package model

import (
	"fmt"

	"to/internal/domain/observer"
)

type User struct {
	ID, Name, Email string
}

func NewUser(ID, name, email string) User {
	return User{ID, name, email}
}

func (u *User) GetID() string {
	return u.ID
}

func (u *User) Notify(event *observer.ReservationEvent) {
	fmt.Printf("Sending email to address <%s> that reservation '%s' was %s\n", u.Email, event.Title, event.Type)
}
