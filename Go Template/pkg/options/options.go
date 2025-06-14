package options

import (
	"github.com/caarlos0/env/v11"
	_ "github.com/joho/godotenv/autoload"

	"to/pkg/utils"
)

type BaseOptions struct {
	Domain string `envDefault:"prod"`
}

func Get[T any]() *T {
	return utils.Ptr(env.Must(env.ParseAsWithOptions[T](env.Options{UseFieldNameByDefault: true})))
}
