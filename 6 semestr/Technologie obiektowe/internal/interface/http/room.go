package http

import (
	"github.com/gin-gonic/gin"

	"to/internal/domain/model"
	"to/internal/domain/roomsorting"
	"to/pkg/utils"
)

type roomUsecase interface {
	Add(*model.Room) error
	Get(string) (**model.Room, error)
	List(roomsorting.SortingType) ([]*model.Room, error)
	Remove(string) (**model.Room, error)
}

type RoomHttpInterface struct {
	httpInterface[*model.Room, roomDTO]
}

type roomUsecaseAdapter struct {
	roomUsecase
}

func (a *roomUsecaseAdapter) List() ([]*model.Room, error) {
	return a.roomUsecase.List("")
}

type roomDTO struct {
	ID       string `json:"id"`
	Name     string `json:"name"`
	Capacity int    `json:"capacity"`
	Floor    int    `json:"floor"`
}

func NewRoomHttpInterface(usecase roomUsecase) *RoomHttpInterface {
	return &RoomHttpInterface{
		httpInterface[*model.Room, roomDTO]{
			usecase: &roomUsecaseAdapter{usecase},
			dtoToEntity: func(dto roomDTO) (*model.Room, error) {
				return utils.Ptr(model.NewRoom(dto.ID, dto.Name, dto.Capacity, dto.Floor)), nil
			},
			entityToDto: func(entity *model.Room) roomDTO {
				return roomDTO{entity.ID, entity.Name, entity.Capacity, entity.Floor}
			},
			usecaseListOverride: func(context *gin.Context) ([]*model.Room, error) {
				return usecase.List(roomsorting.SortingType(context.Query("sort")))
			},
		},
	}
}
