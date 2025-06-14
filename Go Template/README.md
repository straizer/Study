# Go Project Template

A template for Go projects with structured logging and environment configuration support.

## Requirements

- Go 1.24.4 or higher
- Environment configuration file (.env)

## Dependencies

- `github.com/caarlos0/env/v11` - Environment configuration handling
- `github.com/joho/godotenv` - .env file support
- `github.com/stretchr/testify` - Testing utilities
- `go.uber.org/zap` - Structured logging

## Architecture

This project follows Clean Architecture principles:

- Domain Layer - Contains enterprise-wide business rules and entities
- Use Case Layer - Contains application-specific business rules
- Interface Layer - Contains adapters and presenters
- Infrastructure Layer - Contains frameworks and tools

Each layer depends only on the layer below it, following the Dependency Rule.

## Project Structure

- `cmd/` - Application entry points and composition root
- `internal/` - Application internals following Clean Architecture layers:
    - `domain/` - Enterprise business rules and entities
    - `usecase/` - Application business rules
    - `interface/` - Interface adapters (controllers, presenters)
    - `infrastructure/` - Frameworks and external tools
- `pkg/` - Shared utilities and libraries
    - `logger/` - Logging configuration and utilities
    - `options/` - Environment configuration handling

## Installation

1. Clone the repository
2. Copy `.env.example` to `.env` and adjust the values
3. Run `go mod download` to install dependencies
