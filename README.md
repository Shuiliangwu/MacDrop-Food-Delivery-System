# MacDrop project - Group 1

## General Information
- Authors: Hong Li(lih224@mcmaster.ca), Shuiliang Wu(wus55@mcmaster.ca), Fanping Jiang(jiangf13@mcmaster.ca)
- Tests: please follow the test chapter in the report to test the system. Test payloads are introduced in the report.
- Swagger API doc URL: ```http://localhost:${port}/swagger-ui/index.html#/```, please replace ```${port}``` with the corresponding service port.
- Eureka URL: http://localhost:8761
- RabbitMQ URL: http://localhost:15672/#/ (username: guest, password: guest)
- Launch RabbitMQ Docker when developing
  - Pull Rabbit Docker Image: ```docker pull rabbitmq:3-management``` 
  - Run Rabbit Docker: ```docker run -d -p 15672:15672 -p 5672:5672 --name macdrop-rabbitmq rabbitmq:3-management```
- Launch H2 Docker when developing
  - Pull H2 Docker Image: ```docker pull oscarfonts/h2``` 
  - Run H2 Docker: ```docker run -d -p 1521:1521 -p 81:81 -v /your/local/path:/opt/h2-data -e H2_OPTIONS=-ifNotExists --name=macdrop-h2 oscarfonts/h2```
- Launch Redis Docker when developing
  - Pull Redis Image: ```docker pull redislabs/rejson:latest``` 
  - Run Redis Docker: ```docker run -p 6379:6379 --name macdrop-redis redislabs/rejson:latest```

## Deployment
- Go to directory of ```./deployment```
- Run ```docker-compose up --force-recreate``` to deploy all services
- Please wait a few minutes to make all services initialized
- If one of the services goes down, just restart that service
- If fail to run deployment command, please try to keep the version of Docker Compose ```>=v2.12.0```
- JDBC URL: ```jdbc:h2:tcp://localhost:1521/macdrop``` 

## Business requirement coverage
- **All business requirements are designed, implemented and covered in our project**
- In the directory of ```./docs/BusinessRequirementChecklist.pdf```, there is a business requirement checklist summarized from project-1 requirement document. It can be referred to help demonstrate that all business requirements are covered.

## Services diagarm & System architecture
- In the directory of ```./docs/ServiceDiagram(BirdView).pdf```, there is a architecture diagarm introducing what services there are and how they are connected briefly.
- In the directory of ```./docs/ServicesDiagram(DetailedVersion).pdf```, there is a detailed architecture diagarm as the supplement of birdview. 

## Techstack specs
In order to keep development consistent in varying environments among team members, here are techstack specs.

- Java ```8```
- Maven ```3.6.1```
- Spring Boot ```2.7.4, 2.7.5```
- Docker ```20.10.17```
- Docker Compose Version ```v2.12.0```

## Service directory
The followings are all services in the system.
### Infrastructure services
- Eureka (Registry) ```[Port: 8761]```
- RabbitMQ(Message Queue, third-party image) ```[Ports: 15672, 5672]```
- Redis(NoSQL DB, third-party image) ```[Port: 6379]```
- H2(SQL DB, third-party image) ```[Ports: 81, 1521]```

### Internal services
- usermanagement-srv ```[Port: 9092]```
- cartmanagement-srv ```[Port: 9093]```
- accountingbilling-srv ```[Port: 9094]```
- ordermanagement-srv ```[Port: 9095]```
- adminmanagement-srv ```[Port: 9096]```
- notificationmanagement-srv ```[Port: 9097]```
- menumanagement-srv ```[Port: 9098]```

### External services
- externalsys-srv ```[Port: 9091]```

## Useful links
- Launch Eureka server https://www.tutorialspoint.com/spring_boot/spring_boot_eureka_server.htm
- Register a service to Eureka as a Eureka client https://www.tutorialspoint.com/spring_boot/spring_boot_service_registration_with_eureka.htm
- Add Swagger API to Spring Boot https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
- Example of connecting H2 server and CRUD operation https://www.javatpoint.com/spring-boot-h2-database
- Example of CRUD in Redis container from Spring Boot https://developer.redis.com/develop/java/redis-and-spring-course/lesson_6/
- Autoexecution of SQL scripts in Spring Boot https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
- How to access property values in Spring Boot https://stackabuse.com/how-to-access-property-file-values-in-spring-boot/
- How to build Docker image and push it to Docker Hub https://www.techrepublic.com/article/how-to-build-a-docker-image-and-upload-it-to-docker-hub/