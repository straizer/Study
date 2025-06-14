package http

import (
	"net/http"

	"github.com/gin-gonic/gin"

	"to/internal/domain/model"
)

type userUsecase interface {
	Add(*model.User)
	Get(string) (**model.User, error)
	List() []*model.User
	Remove(string) (**model.User, error)
}

type UserHandler struct {
	usecase userUsecase
}

type userDTO struct {
	ID    string `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
}

func NewUserHandler(usecase userUsecase) *UserHandler {
	return &UserHandler{usecase}
}

func (h *UserHandler) Add(c *gin.Context) {
	var req userDTO
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	user := model.NewUser(req.ID, req.Name, req.Email)
	h.usecase.Add(&user)
	c.Status(http.StatusCreated)
}

func (h *UserHandler) Get(c *gin.Context) {
	id := c.Param("id")
	user, err := h.usecase.Get(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := userDTO{(*user).ID, (*user).Name, (*user).Email}
	c.JSON(http.StatusOK, response)
}

func (h *UserHandler) List(c *gin.Context) {
	users := h.usecase.List()
	response := make([]userDTO, len(users))
	for i, user := range users {
		response[i] = userDTO{user.ID, user.Name, user.Email}
	}
	c.JSON(http.StatusOK, response)
}

func (h *UserHandler) Remove(c *gin.Context) {
	id := c.Param("id")
	user, err := h.usecase.Remove(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	response := userDTO{(*user).ID, (*user).Name, (*user).Email}
	c.JSON(http.StatusOK, response)
}
