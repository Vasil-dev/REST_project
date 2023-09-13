FROM openjdk:17-jdk-alpine
WORKDIR app
ARG JAR_FILE=car_rest_project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
