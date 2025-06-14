package repository

import (
	"to/internal/domain/model"
)

type UserRepository struct {
	repository[*model.User]
}

func NewUserRepository() *UserRepository {
	return &UserRepository{*newRepository[*model.User]()}
}
