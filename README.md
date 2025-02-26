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

#### Windows:
### 1. Установите PostgreSQL и создайте базу данных `timetracker`:
   - Скачайте и установите PostgreSQL с [официального сайта](https://www.postgresql.org/download/windows/).
   - После установки, откройте **pgAdmin** или **psql** и выполните команду для создания базы данных:
     ```sql
     CREATE DATABASE timetracker;
     ```

### 2. Укажите настройки базы данных в `application.yml`:
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
### 3. Запустите миграции Liquibase (если используется):
```
mvn liquibase:update
```
### 4. Соберите и запустите приложение:
```
mvn clean install
java -jar target/timetrack-api.jar
```
- Установка и настройка для Linux:
### 1. Установите PostgreSQL и создайте базу данных timetracker:

- Используйте команду для установки:
```
sudo apt-get install postgresql postgresql-contrib
```
- После установки создайте базу данных:
```sql
sudo -u postgres psql
CREATE DATABASE timetracker;
\q
```
### 2. Укажите настройки базы данных в application.yml:
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
### 3. Запустите миграции Liquibase (если используется):
```
mvn liquibase:update
```
### 4. Соберите и запустите приложение:
```
mvn clean install
java -jar target/timetrack-api.jar
```
## 2. Запуск с Docker
- Общие шаги для Windows и Linux:
### 1. Установите Docker и Docker Compose:

- Для Windows, скачайте и установите Docker Desktop с [официального сайта](https://docs.docker.com/desktop/setup/install/windows-install/).
- Для Linux, следуйте инструкциям на [официальном сайте](https://docs.docker.com/engine/install/) Docker.
### 2. Соберите Docker-образ:
```
docker build -t timetrack-api
```
### 3. Запустите контейнер с PostgreSQL и приложением:
```
docker-compose up -d
```
- Это создаст и запустит два контейнера:

- PostgreSQL для базы данных.
- Time Tracker API для сервиса.
### 4. Проверьте, что контейнеры работают:
```
docker ps
```
## API Эндпоинты

### Аутентификация
- `POST /api/auth/login` - Вход пользователя, получение JWT-токена.
- `POST /api/auth/register` - Регистрация нового пользователя.
### Управление временем
- `POST /api/time/start` - Начало трекинга времени.
- `POST /api/time/finish` - Завершение трекинга времени.
### Управление проектами
- `GET /api/projects` - Получение списка проектов.
- `POST /api/projects` - Создание нового проекта (доступно только администратору).
### Пользователи
- `GET /api/users` - Получение списка пользователей (только для админов).
- `PATCH /api/users/{id}` - Обновление данных пользователя.
- `DELETE /api/users/{id}` - Удаление пользователя.
## Роли пользователей
- `USER` - Может отслеживать время и просматривать свои записи.
- `ADMIN` - Управляет пользователями, проектами и ролями.
