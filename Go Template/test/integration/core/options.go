//go:build integration

package core

import (
	"to/cmd/app"
	"to/pkg/options"
)

func getOptions() *app.Options {
	return &app.Options{
		BaseOptions: options.BaseOptions{
			Domain: "prod",
		},
	}
}
