package roomsorting

import (
	"sort"

	"to/internal/domain/model"
)

type ByCapacity struct{}

func (s *ByCapacity) Sort(rooms []*model.Room) []*model.Room {
	sort.SliceStable(rooms, func(i, j int) bool {
		return rooms[i].Capacity < rooms[j].Capacity
	})
	return rooms
}
