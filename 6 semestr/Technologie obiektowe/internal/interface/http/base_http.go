package http

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"to/internal/domain/model"
)

type usecase[T model.Identifiable] interface {
	Add(T) error
	Get(string) (*T, error)
	List() ([]T, error)
	Remove(string) (*T, error)
}

type httpInterface[T model.Identifiable, DTO any] struct {
	usecase usecase[T]

	dtoToEntity func(DTO) (T, error)
	entityToDto func(T) DTO

	usecaseAddOverride    func(*gin.Context, T) error
	usecaseGetOverride    func(*gin.Context) (*T, error)
	usecaseListOverride   func(*gin.Context) ([]T, error)
	usecaseRemoveOverride func(*gin.Context) (*T, error)
}

func (h *httpInterface[T, DTO]) Add(context *gin.Context) {
	var dto DTO
	err := context.ShouldBindJSON(&dto)
	if err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	entity, err := h.dtoToEntity(dto)
	if err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	if h.usecaseAddOverride != nil {
		err = h.usecaseAddOverride(context, entity)
	} else {
		err = h.usecase.Add(entity)
	}
	if err != nil {
		context.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	context.Status(http.StatusCreated)
}

func (h *httpInterface[T, DTO]) Get(context *gin.Context) {
	var entity *T
	var err error
	if h.usecaseGetOverride != nil {
		entity, err = h.usecaseGetOverride(context)
	} else {
		entity, err = h.usecase.Get(context.Param("id"))
	}
	if err != nil {
		context.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	context.JSON(http.StatusOK, h.entityToDto(*entity))
}

func (h *httpInterface[T, DTO]) List(context *gin.Context) {
	var entities []T
	var err error
	if h.usecaseListOverride != nil {
		entities, err = h.usecaseListOverride(context)
	} else {
		entities, err = h.usecase.List()
	}
	if err != nil {
		context.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := make([]DTO, len(entities))
	for i, entity := range entities {
		response[i] = h.entityToDto(entity)
	}
	context.JSON(http.StatusOK, response)
}

func (h *httpInterface[T, DTO]) Remove(context *gin.Context) {
	var entity *T
	var err error
	if h.usecaseGetOverride != nil {
		entity, err = h.usecaseRemoveOverride(context)
	} else {
		entity, err = h.usecase.Remove(context.Param("id"))
	}
	if err != nil {
		context.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	context.JSON(http.StatusOK, h.entityToDto(*entity))
}
