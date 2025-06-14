//go:build integration

package integration

import (
	"os"
	"testing"

	"to/test/integration/core"
)

func TestMain(m *testing.M) {
	application := core.StartApp()
	exitCode := m.Run()
	core.StopApp(application)
	os.Exit(exitCode)
}
