FROM ubuntu:18.04

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

WORKDIR /app

COPY build/libs/wirebarley_service.jar /app/app.jar
ENV	USE_PROFILE prod

CMD ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "app.jar"]