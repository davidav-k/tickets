# tickets

A microservice application for managing event ticket sales. The main focus is on security, scalability and event architecture.

## Architecture

1. Keycloak — user authentication and authorization + PostgreSQL — user data storage
2. Spring Boot microservices:
   - api-gateway — routing, filtering, JWT verification
   - ticket-service — ticket business logic + PostgreSQL for tickets data storage
   - cashier-service — ticket sales and cancellation
   - notification-service — sending notifications
   - scanner-service — ticket validation and redemption
3. Kafka — event broker (ticket purchase, notifications, etc.)
4. Vue frontend — user interface
5. Centralized logging via Kafka

## Goal

Minimal launch with working security:

1. Authorization via Keycloak
2. JWT verification in api-gateway
3. Secure REST endpoints in ticket-service
4. Authorization and authentication via frontend

## Ports

| Service                | Port |
|------------------------|------|
| Keycloak               | 9090 |
| PostgreSQL for users   | 5543 |
| PostgreSQL for tickets | 5432 |
| API Gateway            | 8088 |
| Ticket Service         | 8091 |
| Vue Frontend           | 5173 |

## Current progress

1. Keycloak:
   - Keycloak (port 9090) is configured with PostgreSQL as a database (port 5543) with Docker
   - Realm "tickets" is created in Keycloak
   - Client "api-gateway" is configured with access type confidential
   - Client "vue-frontend" is configured with access type public
   - Users:
   - "admin@tickets.local", password "Password123" has ROLE_ADMIN
   - "cashier@tickets.local", password "Password123" has ROLE_CASHIER
   - "user@tickets.local", password "Password123" has ROLE_USER
2. API Gateway:
   - is configured to use Keycloak for authentication
   - JWT verification is implemented
   - routes to ticket-service are set up
3. Ticket Service:
   - is configured to use Keycloak for authentication
   - secure REST endpoints are implemented
   - PostgreSQL for tickets data is running on port 5432
   - JWT verification is implemented
   - roles are checked in endpoints
4. Next step Vue Frontend:
   - is configured to use Keycloak for authentication
   - login and registration forms are implemented
   - JWT token is stored in local storage
   - API calls to api-gateway are made with JWT token

## Project structure

tickets/
├── backend/
│ ├── api-gateway/
│ ├── ticket-service/
│ ├── cashier-service/
│ ├── notification-service/
│ └── scanner-service/
├── frontend/
│ └── vue-frontend/
├── infrastructure/
│ └── keycloak/
│ ├── db/
│ └── realm-export.json
├── docker-compose.yml
└── README.md

## How to run
1. run `docker-compose up --build` in the root directory of the project
2. wait for all services to start (it may take a few minutes)
3. run local API Gateway, Ticket Service 

#### Links:
| service        | link                  |
|----------------|-----------------------|
| Keycloak       | http://localhost:9090 |
| API Gateway    | http://localhost:8088 |
| Ticket Service | http://localhost:8091 |
| Vue Frontend   | http://localhost:5173 |

---
#### Keycloak Admin Console:
http://localhost:9090/admin
Username: admin
Password: admin