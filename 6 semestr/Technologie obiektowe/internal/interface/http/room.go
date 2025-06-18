package http

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"to/internal/domain/model"
	"to/internal/domain/roomsorting"
)

type roomUsecase interface {
	Add(*model.Room)
	Get(string) (**model.Room, error)
	List(roomsorting.SortingType) []*model.Room
	Remove(string) (**model.Room, error)
}

type RoomHttpInterface struct {
	usecase roomUsecase
}

type roomDTO struct {
	ID       string `json:"id"`
	Name     string `json:"name"`
	Capacity int    `json:"capacity"`
	Floor    int    `json:"floor"`
}

func NewRoomHttpInterface(usecase roomUsecase) *RoomHttpInterface {
	return &RoomHttpInterface{usecase}
}

func (h *RoomHttpInterface) Add(c *gin.Context) {
	var req roomDTO
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	room := model.NewRoom(req.ID, req.Name, req.Capacity, req.Floor)
	h.usecase.Add(&room)
	c.Status(http.StatusCreated)
}

func (h *RoomHttpInterface) Get(c *gin.Context) {
	id := c.Param("id")
	room, err := h.usecase.Get(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := roomDTO{(*room).ID, (*room).Name, (*room).Capacity, (*room).Floor}
	c.JSON(http.StatusOK, response)
}

func (h *RoomHttpInterface) List(c *gin.Context) {
	rooms := h.usecase.List(roomsorting.SortingType(c.Query("sort")))
	resp := make([]roomDTO, len(rooms))
	for i, room := range rooms {
		resp[i] = roomDTO{room.ID, room.Name, room.Capacity, room.Floor}
	}
	c.JSON(http.StatusOK, resp)
}

func (h *RoomHttpInterface) Remove(c *gin.Context) {
	id := c.Param("id")
	room, err := h.usecase.Remove(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := roomDTO{(*room).ID, (*room).Name, (*room).Capacity, (*room).Floor}
	c.JSON(http.StatusOK, response)
}
