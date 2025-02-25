# Time Tracker API

## Описание
Time Tracker API - это сервис для управления временем и проектами. Он позволяет пользователям отслеживать рабочее время, управлять проектами и ролями пользователей.

## Технологии
- **Java 17**
- **Spring Boot** (Spring Security, Spring Data JPA, Spring Web, JWT, Maven, Docker, Liquibase)
- **PostgreSQL** (база данных)
- **Liquibase** (миграции базы данных)
- **JWT** (аутентификация и авторизация)
- **Docker** (контейнеризация)
- **Maven** (сборка проекта)

## Запуск проекта

### 1. Локальный запуск (без Docker)
1. Установите PostgreSQL и создайте базу данных `timetracker`.
2. Укажите настройки базы данных в `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/timetracker
       username: yourusername
       password: yourpassword
     jpa:
       hibernate:
         ddl-auto: validate
   ```
3. Запустите миграции Liquibase (если используется).
4. Соберите и запустите приложение:
   ```sh
   mvn clean install
   java -jar target/timetrack-api.jar
   ```

### 2. Запуск с Docker
1. Соберите Docker-образ:
   ```sh
   docker build -t timetrack-api .
   ```
2. Запустите контейнер с PostgreSQL и приложением:
   ```sh
   docker-compose up -d
   ```

## API Эндпоинты

### Аутентификация
- `POST /auth/login` - вход пользователя, получение JWT-токена.
- `POST /auth/register` - регистрация нового пользователя.

### Управление временем
- `POST /time/start` - начало трекинга времени.
- `POST /time/finish` - завершение трекинга времени.

### Управление проектами
- `GET /projects` - получение списка проектов.
- `POST /projects` - создание нового проекта (доступно только администратору).

### Пользователи
- `GET /users` - получение списка пользователей (только для админов).
- `PATCH /users/{id}` - обновление данных пользователя.

## Роли пользователей
- `USER` - может отслеживать время и просматривать свои записи.
- `ADMIN` - управляет пользователями, проектами и ролями.

