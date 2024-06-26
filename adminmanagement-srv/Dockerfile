FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/adminmanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/adminmanagement-srv.jar
CMD ["java", "-jar", "/opt/app/adminmanagement-srv.jar"]