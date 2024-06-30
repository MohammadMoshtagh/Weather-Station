FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

ENTRYPOINT ["java", "-jar", "target/user-management-0.0.1-SNAPSHOT.jar"]