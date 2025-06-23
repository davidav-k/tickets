
# tickets

Микросервисное приложение для управления продажей билетов на мероприятия. Основной упор сделан на безопасность, масштабируемость и событийную архитектуру.

## Архитектура

1. Keycloak — аутентификация и авторизация пользователей (через Docker)
2. PostgreSQL — хранилище данных пользователей и билетов
3. Spring Boot микросервисы:
    - api-gateway — маршрутизация, фильтрация, проверка JWT
    - ticket-service — бизнес-логика билетов
    - cashier-service — продажа и отмена билетов
    - notification-service — отправка уведомлений
    - scanner-service — валидация и погашение билетов
4. Kafka — брокер событий (покупка билета, уведомления и др.)
5. Vue frontend — интерфейс пользователя
6. Централизованное логирование через Kafka (в следующих этапах)

## Цель

Минимальный запуск с работающей безопасностью:

1. Авторизация через Keycloak
2. Проверка JWT в api-gateway
3. Защищенные REST endpoints в ticket-service
4. Авторизация и аутентификация через frontend

## Порты

Сервис | Порт
-------|-----
Keycloak | 9090
PostgreSQL | 5543
API Gateway | 8088
Ticket Service | 8091
Vue Frontend | 5173 (планируется)

## Текущий прогресс

1. Keycloak и PostgreSQL настроены через Docker
2. Realm tickets создан вручную или импортирован
3. Пользователь admin@tickets.local добавлен
4. Роли admin, ROLE_USER, ROLE_CASHIER определены
5. Клиенты api-gateway и vue-frontend настроены
6. Следующий этап — реализация api-gateway и ticket-service с безопасностью

## Структура проекта

tickets/
├── backend/
│   ├── api-gateway/
│   ├── ticket-service/
│   ├── cashier-service/
│   ├── notification-service/
│   └── scanner-service/
├── frontend/
│   └── vue-frontend/
├── infrastructure/
│   └── keycloak/
│       ├── db/
│       └── realm-export.json
├── docker-compose.yml
└── README.md

## Команда запуска базовых сервисов

docker-compose up --build

Keycloak доступен по адресу:

http://localhost:9090/admin

Данные для входа (временно):

Username: admin  
Password: admin
