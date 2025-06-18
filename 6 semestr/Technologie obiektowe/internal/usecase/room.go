package usecase

import (
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

func (uc *roomUsecase) List(sortOption roomsorting.SortingType) []*model.Room {
	rooms := uc.usecase.List()
	var sorter roomSortStrategy
	switch sortOption {
	case roomsorting.ByCapacity:
		sorter = &roomsorting.SortByCapacity{}
	case roomsorting.ByFloor:
		sorter = &roomsorting.SortByFloor{}
	default:
		sorter = nil
	}
	if sorter != nil {
		rooms = sorter.Sort(rooms)
	}
	return rooms
}
