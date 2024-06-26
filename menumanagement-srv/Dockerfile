FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/menumanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/menumanagement-srv.jar
CMD ["java", "-jar", "/opt/app/menumanagement-srv.jar"]