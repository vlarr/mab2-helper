FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
WORKDIR /app
VOLUME /app/db
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=docker"]