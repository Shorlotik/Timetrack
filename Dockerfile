FROM openjdk:17-jdk-slim
COPY target/timetrack.jar timetrack.jar
ENTRYPOINT ["java", "-jar", "/timetrack.jar"]
