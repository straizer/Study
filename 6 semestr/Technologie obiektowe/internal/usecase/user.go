package usecase

import (
	"to/internal/domain/model"
)

type userUsecase struct {
	usecase[*model.User]
}

func NewUserUsecase(repository repository[*model.User]) *userUsecase {
	return &userUsecase{usecase[*model.User]{repository}}
}
