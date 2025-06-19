package usecase

import (
	"fmt"

	"to/internal/domain/model"
	"to/internal/domain/roomsorting"
)

type roomSortStrategy interface {
	Sort([]*model.Room) []*model.Room
}

type roomUsecase struct {
	usecase[*model.Room]
}

func NewRoomUsecase(repository repository[*model.Room]) *roomUsecase {
	return &roomUsecase{usecase[*model.Room]{repository}}
}

func (uc *roomUsecase) List(sortOption roomsorting.SortingType) ([]*model.Room, error) {
	rooms, err := uc.usecase.List()
	if err != nil {
		return nil, fmt.Errorf("failed to list all rooms: %w", err)
	}
	var sorter roomSortStrategy
	switch sortOption {
	case roomsorting.ByCapacity:
		sorter = &roomsorting.SortByCapacity{}
	case roomsorting.ByFloor:
		sorter = &roomsorting.SortByFloor{}
	}
	if sorter != nil {
		rooms = sorter.Sort(rooms)
	}
	return rooms, nil
}
