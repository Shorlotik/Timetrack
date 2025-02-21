FROM openjdk:17-jdk
WORKDIR /app
RUN mvn clean package
COPY target/timetracker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
