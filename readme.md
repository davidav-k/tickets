# Tickets Service - v0.2.02

## Overview
Tickets is a microservice-based application for managing event ticket sales with a focus on security, scalability, and event-driven architecture.

## Features in v0.2.02
This release includes:
- Centralized API gateway for routing requests
- Standardized API response format
- OpenAPI/Swagger documentation
- User authentication via Keycloak
- JWT token validation
- Role-based access control (ADMIN, CASHIER, USER)
- API gateway with security context relay
- Service-to-service secure communication
- Ticket service with CRUD operations
- Event service integration
- Microservice security with custom filters
- Cross-service user synchronization

## System Requirements
- Docker and Docker Compose
- Java 17+
- Maven 3.8+

## Installation and Setup

1. Create a `.env` file in the root directory with the example content in file `.env.example`. 
This file contains environment variables for the services.
2. Start the infrastructure services:
   ```bash
   docker-compose up --build
   ```
3. Run the local services:
   - API Gateway
   - Ticket Service
   - Event Service

## Services

| Service                | URL                    | Port | Description                          |
|--------------------------|------------------------|------|--------------------------------------|
| Keycloak                | http://localhost:9090  | 9090 | Authentication server                |
| Keycloak Admin Console  | http://localhost:9090/admin | 9090 | Admin: admin / Password: admin  |
| PostgreSQL (users)      | -                      | 5543 | Keycloak user database              |
| PostgreSQL (tickets)    | -                      | 5432 | Tickets database                     |
| API Gateway             | http://localhost:8088  | 8088 | Routing and security                |
| Ticket Service          | http://localhost:8091  | 8091 | Core ticket functionality           |
| Event Service           | http://localhost:8092  | 8092 | Event management                    |
| Vue Frontend            | http://localhost:5173  | 5173 | User interface                      |

## Test Users

| Username               | Password     | Role         |
|------------------------|--------------|--------------|
| admin@tickets.local    | Password123  | ROLE_ADMIN   |
| cashier@tickets.local  | Password123  | ROLE_CASHIER |
| user@tickets.local     | Password123  | ROLE_USER    |

## Authentication Flow
- Users authenticate through the API Gateway using Keycloak
- API Gateway validates JWT tokens and extracts user roles
- User context is propagated to microservices via secure headers
- Microservices validate headers and enforce permissions

## Service-to-Service Communication
- Services communicate using WebClient
- Security context is propagated between services
- Gateway secret validates inter-service communication
- User roles and permissions are maintained across service boundaries

## Current Features
- Complete authentication flow
- Role-based access control
- Ticket creation and management
- Event lookup and integration
- User synchronization across services
- Secure inter-service communication

## Upcoming Features
- Payment processing
- Email notifications
- Ticket scanning and validation
- Advanced reporting
- Cashier service for ticket sales
- Notification service for alerts and updates
- Scanner service for ticket validation

## Project Structure
```
tickets/
├── backend/
│   ├── api-gateway/
│   ├── ticket-service/
│   ├── event-service/
│   ├── cashier-service/ (planned)
│   ├── notification-service/ (planned)
│   └── scanner-service/ (planned)
├── frontend/
│   └── vue-frontend/
├── infrastructure/
│   └── keycloak/
│       ├── db/
│       └── realm-export.json
├── docker-compose.yml
└── README.md
```

## License
Proprietary - All rights reserved