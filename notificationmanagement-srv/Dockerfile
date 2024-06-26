FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/notificationmanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/notificationmanagement-srv.jar
CMD ["java", "-jar", "/opt/app/notificationmanagement-srv.jar"]
