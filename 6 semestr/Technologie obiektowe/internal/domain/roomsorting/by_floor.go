package roomsorting

import (
	"sort"

	"to/internal/domain/model"
)

type SortByFloor struct{}

func (s *SortByFloor) Sort(rooms []*model.Room) []*model.Room {
	sort.SliceStable(rooms, func(i, j int) bool {
		return rooms[i].Floor < rooms[j].Floor
	})
	return rooms
}
