package repository

import (
	"to/internal/domain/model"
)

type RoomRepository struct {
	repository[*model.Room]
}

func NewRoomRepository() *RoomRepository {
	return &RoomRepository{*newRepository[*model.Room]()}
}
