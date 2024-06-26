FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/cartmanagement-srv-0.0.1-SNAPSHOT.jar /opt/app/cartmanagement-srv.jar
CMD ["java", "-jar", "/opt/app/cartmanagement-srv.jar"]
