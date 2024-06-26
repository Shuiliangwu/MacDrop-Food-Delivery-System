FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/externalsys-srv-0.0.1-SNAPSHOT.jar /opt/app/externalsys-srv.jar
CMD ["java", "-jar", "/opt/app/externalsys-srv.jar"]
