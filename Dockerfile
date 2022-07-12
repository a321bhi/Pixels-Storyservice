FROM openjdk:17

COPY target/pixels-feedservice-1.jar pixels-feedservice.jar

EXPOSE 8102

ENTRYPOINT ["java","-jar","pixels-feedservice.jar"]
