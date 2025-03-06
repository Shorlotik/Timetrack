# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY target/timetrack-1.0.0.jar timetrack-1.0.0.jar

COPY .env /app/.env

COPY pom.xml /app/pom.xml

# Запуск приложения
ENTRYPOINT ["java", "-jar", "timetrack-1.0.0.jar"]
