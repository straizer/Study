FROM golang:latest AS dependencies
RUN go install github.com/golangci/golangci-lint/v2/cmd/golangci-lint@latest
RUN go install gotest.tools/gotestsum@latest
WORKDIR /source
COPY go.mod go.sum ./
RUN go mod download
COPY . .

FROM dependencies AS lint
RUN golangci-lint run

FROM lint AS unit-test
RUN gotestsum --format testdox

FROM unit-test AS integration-test
RUN gotestsum --format testdox -- -tags=integration ./test/integration

FROM integration-test AS build
RUN CGO_ENABLED=0 GOOS=linux go build -C cmd/ -o /app

FROM scratch AS release
COPY --from=build /app /app
ENTRYPOINT ["/app"]
