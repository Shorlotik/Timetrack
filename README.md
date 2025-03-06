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
    username: postgres
    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  liquibase:
    enabled: true
    change-log: classpath:/db/changelog/db.changelog-master.yaml

application:
  jwt:
    secret: ${JWT_SECRET:default_secret_key}
    expiration: ${JWT_EXPIRATION:3600000}  # 1 час по умолчанию
```
### 3. Запустите миграции Liquibase (если используется):
```
mvn liquibase:update
```
### 4. Соберите и запустите приложение:
```
mvn clean install
java -jar target/timetrack.jar
```
### Linux:
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
java -jar target/timetrack.jar
```
## 2. Запуск с Docker
- Общие шаги для Windows и Linux:
### 1. Установите Docker и Docker Compose:

- Для Windows, скачайте и установите Docker Desktop с [официального сайта](https://docs.docker.com/desktop/setup/install/windows-install/).
- Для Linux, следуйте инструкциям на [официальном сайте](https://docs.docker.com/engine/install/) Docker.
### 2. Соберите Docker-образ:
# Windows
```
docker-compose up --build -d
```
# Linux
```
sudo docker-compose up --build -d
```
- Команда docker-compose up --build -d автоматически соберет Docker-образ и запустит контейнеры:

- PostgreSQL для базы данных.

- Time Tracker API для сервиса.
### 3. Проверка что контейнеры работают:
```
docker ps
```
## API Эндпоинты

### Аутентификация
- `POST /api/auth/login` - Вход пользователя, получение JWT-токена.
- `POST /api/auth/register` - Регистрация нового пользователя.
- `POST /api/auth/loguot` - Выход пользователя.
### Управление временем
- `POST /api/records/start?projectId=1&username=testuser` - Начало трекинга времени. (testuser поменять на совего пользователя)
- `POST /api/records/finish?projectId=1&username=testuser` - Завершение трекинга времени. (testuser поменять на совего пользователя)
- `GET /api/records` - Вывод всех трекингов времени
- `GET /api/records/user/testuser` - Вывод времени отпределенного пользователя (testuser поменять на совего пользователя)
- `GET /api/records/project/{id}` - Вывод времени проекта ({id}    поменять на id проекта)
- `GET /api/records/{id}` - Вывод трекинга времени по ID (заменить на нужный ID)
- `GET /api/records/between?startDate=2025-03-01T00:00:00Z&endDate=2025-03-05T23:59:59Z` - Получение трекинга времени на отпределенной дате (заменить на нужную дату из finsh)
- `PATCH /api/records/{id}` - Обновление трекинга времени (заменить на нужны id)
- `DELETE /api/records/{id}` - Удаление определенного трекинга времени (заменить на нужный id)
- `DELETE /api/records/user/testuser` - Удаление пользователя из трекинга времени (testuser поменять на совего пользователя)
### Управление проектами
- `GET /api/projects` - Получение списка проектов.
- `POST /api/projects` - Создание нового проекта (доступно только администратору).
- `GET /api/projects/{id}` - Получение списка проектов по id. (заменить на нужный id)
- `PUT /api/projects/{id}` - Обновление данных проекта по id. (заменить на нужный id)
- `DELETE /api/projects/{id}` - Удаление проекта.
- `GET /api/projects/search?name=TestProject` - Поиск проекта по названию. (TestProject заменить на нужное название проекта)
- `GET /api/projects/user/{id}` - Получение проектов пользователя. (заменить на нужное id)
### Пользователи
- `POST /api/users` - Создание пользователя. (только для админов)
- `GET /api/users/{id}` - Получить пользователя по ID.
- `GET /api/users` - Получение списка пользователей (только для админов).
- `PATCH /api/users/{id}` - Обновление данных пользователя.
- `DELETE /api/users/{id}` - Удаление пользователя.
- `GET /api/users/find?username=testuser` - Поиск пользователя по имени. (testuser поменять на совего пользователя)
## Роли пользователей
- `USER` - Может отслеживать время и просматривать свои записи.
- `ADMIN` - Управляет пользователями, проектами и ролями.
