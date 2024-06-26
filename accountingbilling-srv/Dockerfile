FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/accountingbilling-srv-0.0.1-SNAPSHOT.jar /opt/app/accountingbilling-srv.jar
CMD ["java", "-jar", "/opt/app/accountingbilling-srv.jar"]
