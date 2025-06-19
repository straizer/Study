# Room Reservation Management System

## Project Description
This project implements a room reservation management system with the following main capabilities:
- User management
- Room management
- Reservation management and validation

## Main Features

### User Module
- Add a user
- Get a user
- Get a list of users
- Delete a user

### Room Module
- Add a room
- Get a room
- Get a list of rooms sorted by specified criteria
- Delete a room

### Reservation Module
- Add a reservation with organizer and invitees, including validation of start and end dates/times
- Get a reservation
- Get a list of reservations
- Delete a reservation

## Design Patterns
The project implements several design patterns and follows design principles such as SOLID, DRY, YAGNI, and KISS:

### Observer Pattern
- Organizers and invitees are notified when a reservation is created or cancelled
- Implemented through Observer and Subject interfaces in the domain layer

### Strategy Pattern
- Room sorting is performed based on the parameter passed in the HTTP request
- Different sorting strategies (by capacity, by floor) are implemented as separate classes

### Adapter Pattern
- Room handling in the Usecase layer requires an additional adapter implementing a common interface for all Usecases due to the Strategy pattern implementation

## Architectural Patterns
The project is built using "Clean Architecture" - an extended version of hexagonal architecture.

### Domain Layer
- Contains models representing basic entities without business logic
- Includes User, Room, and Reservation classes, all implementing the Identifiable interface
- Defines interfaces used by the Observer and Strategy patterns

### Infrastructure Layer
- Represents the infrastructure used by the program (downstream outputs)
- Includes Repository implementation with:
  - Repository<Identifiable> interface
  - BaseRepository<Identifiable> abstract class
  - Concrete implementations: UserRepository, RoomRepository, ReservationRepository

### Usecase Layer
- Represents business logic
- Uses infrastructure to perform its functionalities
- Includes:
  - Usecase<Identifiable> interface
  - BaseUsecase<Identifiable> abstract class
  - Concrete implementations: UserUsecase, RoomUsecase, ReservationUsecase

### Interface Layer
- Represents ways to interact with the program (upstream inputs)
- Implemented as HTTP interfaces:
  - HttpInterface<Identifiable> interface
  - BaseHttpInterface<Identifiable> abstract class
  - Concrete implementations: UserHttpInterface, RoomHttpInterface, ReservationHttpInterface

## Installation and Usage

### Prerequisites
- Go (latest version recommended)
- Docker and Docker Compose (for containerized deployment)

### Setup
1. Clone the repository
2. Navigate to the project directory
3. Run `go mod download` to download dependencies

### Running the Application
- Local development: `go run cmd/main.go`
- Using Docker: `docker-compose up`

### API Endpoints

#### Users
- `GET /users` - Get all users
- `GET /users/{id}` - Get a specific user
- `POST /users` - Create a new user
- `DELETE /users/{id}` - Delete a user

#### Rooms
- `GET /rooms` - Get all rooms
- `GET /rooms?sort={criteria}` - Get all rooms sorted by criteria (capacity, floor)
- `GET /rooms/{id}` - Get a specific room
- `POST /rooms` - Create a new room
- `DELETE /rooms/{id}` - Delete a room

#### Reservations
- `GET /reservations` - Get all reservations
- `GET /reservations/{id}` - Get a specific reservation
- `POST /reservations` - Create a new reservation
- `DELETE /reservations/{id}` - Delete a reservation

## License
This project is licensed under the MIT License - see the LICENSE file for details.
