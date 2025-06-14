package usecase

import (
	"fmt"

	"to/internal/domain/model"
)

type repository[T model.Identifiable] interface {
	Save(T)
	FindAll() []T
	FindByID(string) (T, error)
	Delete(string)
}

type usecase[T model.Identifiable] struct {
	repository repository[T]
}

func (uc *usecase[T]) Add(entity T) {
	uc.repository.Save(entity)
}

func (uc *usecase[T]) Get(entityID string) (*T, error) {
	entity, err := uc.repository.FindByID(entityID)
	if err != nil {
		return nil, fmt.Errorf("no entity of type <%T> with ID <%s>: %w", entity, entityID, err)
	}
	return &entity, nil
}

func (uc *usecase[T]) List() []T {
	return uc.repository.FindAll()
}

func (uc *usecase[T]) Remove(userID string) (*T, error) {
	entity, err := uc.Get(userID)
	if err != nil {
		return nil, fmt.Errorf("entity of type <%T> not found: %w", entity, err)
	}
	uc.repository.Delete(userID)
	return entity, nil
}
