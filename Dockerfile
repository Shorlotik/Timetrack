FROM maven:3.9.9 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B # зависимости (кешируется Docker)

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
