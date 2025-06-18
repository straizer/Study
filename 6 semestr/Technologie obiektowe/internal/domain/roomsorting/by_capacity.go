package roomsorting

import (
	"sort"

	"to/internal/domain/model"
)

type SortByCapacity struct{}

func (s *SortByCapacity) Sort(rooms []*model.Room) []*model.Room {
	sort.SliceStable(rooms, func(i, j int) bool {
		return rooms[i].Capacity < rooms[j].Capacity
	})
	return rooms
}
