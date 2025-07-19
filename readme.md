# Tickets Service - v0.1.0-beta

## Overview
Tickets is a microservice-based application for managing event ticket sales with a focus on security, scalability, and event-driven architecture.

## Features in v0.1.0-beta
This beta release focuses on establishing the security infrastructure:

- User authentication via Keycloak
- JWT token validation
- Role-based access control
- Basic API gateway routing
- Initial ticket service functionality
- Role-based access control for ticket management
- Frontend authentication flow

## System Requirements
- Docker and Docker Compose
- Java 17+
- Maven 3.8+

## Installation and Setup


1. Start the infrastructure services:
   ```bash
   docker-compose up --build
   ```
2. Run the local services:
   - API Gateway
   - Ticket Service

## Services

| Service                | URL                    | Port | Description                          |
|--------------------------|------------------------|------|--------------------------------------|
| Keycloak                | http://localhost:9090  | 9090 | Authentication server                |
| Keycloak Admin Console  | http://localhost:9090/admin | 9090 | Admin: admin / Password: admin  |
| PostgreSQL (users)      | -                      | 5543 | Keycloak user database              |
| PostgreSQL (tickets)    | -                      | 5432 | Tickets database                     |
| API Gateway             | http://localhost:8088  | 8088 | Routing and security                |
| Ticket Service          | http://localhost:8091  | 8091 | Core ticket functionality           |
| Vue Frontend            | http://localhost:5173  | 5173 | User interface                      |

## Test Users

| Username               | Password     | Role         |
|------------------------|--------------|--------------|
| admin@tickets.local    | Password123  | ROLE_ADMIN   |
| cashier@tickets.local  | Password123  | ROLE_CASHIER |
| user@tickets.local     | Password123  | ROLE_USER    |

## Current Limitations
- Limited ticket management functionality
- No payment processing
- No notification service integration
- Basic frontend with authentication only

## Upcoming Features
- Frontend UI
- Complete ticket CRUD operations
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