FROM openjdk:16-alpine3.13
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENV LANG pt_BR.UTF-8.
ENV LANGUAGE=pt_BR:en_US:en
ENV LC_ALL pt_BR.UTF-8
ENTRYPOINT ["java", "-jar", "application.jar"]