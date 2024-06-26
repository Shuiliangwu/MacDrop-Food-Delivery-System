FROM eclipse-temurin:18-alpine

RUN mkdir /opt/app
COPY ./target/eureka-infra-0.0.1-SNAPSHOT.jar /opt/app/eureka-infra.jar
CMD ["java", "-jar", "/opt/app/eureka-infra.jar"]
