FROM openjdk:17

COPY target/pixels-storyservice-1.jar pixels-storyservice.jar

EXPOSE 8102

ENTRYPOINT ["java","-jar","pixels-storyservice.jar"]
