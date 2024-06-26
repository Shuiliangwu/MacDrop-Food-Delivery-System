FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/ordermanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/ordermanagement-srv.jar
CMD ["java", "-jar", "/opt/app/ordermanagement-srv.jar"]
