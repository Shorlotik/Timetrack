FROM openjdk:17-jdk
WORKDIR /app
COPY target/timetracker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
