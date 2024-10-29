FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y curl

COPY build/libs/me-to-you-0.0.1-SNAPSHOT.jar /app/me-to-you.jar

ENTRYPOINT ["java", "-jar", "/app/me-to-you.jar"]