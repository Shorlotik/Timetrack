# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем скомпилированный JAR-файл
COPY target/timetrack-1.0.0.jar timetrack-1.0.0.jar

# Копируем файл .env (чтобы переменные загружались)
COPY .env .env

# Запуск приложения
ENTRYPOINT ["java", "-jar", "timetrack-1.0.0.jar"]
