# Vue Frontend

This is the frontend application built with Vue.js for the Task Management Platform. It provides an intuitive user interface for managing tasks and interacting with users. The frontend communicates with backend services for user authentication, task management, and notifications.

## Features:
- User registration and login
- Manage ticket (buy, cancel, refund)
- Display real-time notifications
- Role-based access control (admins can manage all tickets, cashiers can manage their own tickets, users can view their tickets)

## Technologies:
- Vue.js 3
- Vue Router for navigation
- Vuex for state management
- Axios for HTTP requests

## Primary frontend development:

- Create basic Vue.js components to display events, halls, and tickets.
- Implement Keycloak authorization using OAuth 2.0 Authorization Code Flow (as specified in get-gwt-via-auth-code.ps1).
- Use Axios to interact with the api-gateway (e.g. /api/events and /api/tickets endpoints).
- Add basic pages:
Event list (GET /api/events).
Event details (GET /api/events/{id}).
Purchase a ticket (POST /api/tickets).
A personal account to view user tickets (GET /api/tickets/user/{userId}).
