FROM openjdk:17
COPY target/timetracker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
