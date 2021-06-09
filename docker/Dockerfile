FROM openjdk:17-jdk-alpine3.13
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENV LANG=pt_BR.UTF-8
ENTRYPOINT ["java", "-jar", "application.jar"]