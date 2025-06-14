package repository

import (
	"errors"
	"sync"

	"to/internal/domain/model"
)

type repository[T model.Identifiable] struct {
	mu       sync.RWMutex
	entities map[string]T
}

func newRepository[T model.Identifiable]() *repository[T] {
	return &repository[T]{entities: make(map[string]T)}
}

func (r *repository[T]) Save(entity T) {
	r.mu.Lock()
	defer r.mu.Unlock()
	r.entities[entity.GetID()] = entity
}

func (r *repository[T]) FindAll() []T {
	r.mu.RLock()
	defer r.mu.RUnlock()
	var list []T
	for _, entity := range r.entities {
		list = append(list, entity)
	}
	return list
}

func (r *repository[T]) FindByID(id string) (T, error) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	entity, ok := r.entities[id]
	if !ok {
		var empty T
		return empty, errors.New("entity not found")
	}
	return entity, nil
}

func (r *repository[T]) Delete(id string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	delete(r.entities, id)
}
