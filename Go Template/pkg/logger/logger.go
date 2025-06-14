package logger

import (
	"fmt"
	"strings"

	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
)

func Initialize(domain string) {
	zap.ReplaceGlobals(getLogger(domain))
	zap.L().Info("logger initialized", zap.String("domain", domain))
}

func getLogger(domain string) *zap.Logger {
	switch strings.ToLower(domain) {
	case "dev":
		return getDevelopmentLogger()
	case "prod":
		return getProductionLogger()
	default:
		panic(fmt.Errorf("domain must be one of <dev, prod>, but was <%s>", domain))
	}
}

func getDevelopmentLogger() *zap.Logger {
	config := zap.NewDevelopmentConfig()
	config.EncoderConfig.EncodeCaller = zapcore.FullCallerEncoder
	config.EncoderConfig.EncodeLevel = zapcore.CapitalColorLevelEncoder
	config.OutputPaths = []string{"stdout"}
	logger, err := config.Build()
	if err != nil {
		fmt.Printf("failed to create a custom logger: %s\n", err.Error())
		return zap.Must(zap.NewDevelopment())
	}
	return logger
}

func getProductionLogger() *zap.Logger {
	config := zap.NewProductionConfig()
	config.OutputPaths = []string{"stdout"}
	logger, err := config.Build()
	if err != nil {
		fmt.Printf("failed to create a custom logger: %s\n", err.Error())
		return zap.Must(zap.NewProduction())
	}
	return logger
}
