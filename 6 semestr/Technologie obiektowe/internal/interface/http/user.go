package http

import (
	"to/internal/domain/model"
	"to/pkg/utils"
)

type UserHttpInterface struct {
	httpInterface[*model.User, userDTO]
}

type userDTO struct {
	ID    string `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
}

func NewUserHttpInterface(usecase usecase[*model.User]) *UserHttpInterface {
	return &UserHttpInterface{
		httpInterface[*model.User, userDTO]{
			usecase: usecase,
			dtoToEntity: func(dto userDTO) (*model.User, error) {
				return utils.Ptr(model.NewUser(dto.ID, dto.Name, dto.Email)), nil
			},
			entityToDto: func(entity *model.User) userDTO {
				return userDTO{entity.ID, entity.Name, entity.Email}
			},
		},
	}
}
