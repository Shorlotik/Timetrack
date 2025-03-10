FROM maven:3.9.9 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

COPY --from=build /app/target/timetrack-1.0.0.jar app.jar

COPY .env .env

ENTRYPOINT ["java", "-jar", "app.jar"]
