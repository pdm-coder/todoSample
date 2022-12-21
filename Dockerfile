FROM openjdk:8
COPY ./target/todoapp-0.0.1-SNAPSHOT.jar todoapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","todoapp-0.0.1-SNAPSHOT.jar"]