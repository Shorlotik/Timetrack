# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY target/timetrack.jar timetrack.jar

# Запуск приложения
ENTRYPOINT ["java", "-jar", "timetrack.jar"]
