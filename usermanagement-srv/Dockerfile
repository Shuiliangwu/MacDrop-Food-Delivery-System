FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/usermanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/usermanagement-srv.jar
CMD ["java", "-jar", "/opt/app/usermanagement-srv.jar"]
