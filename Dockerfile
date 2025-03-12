FROM amazoncorretto:21
MAINTAINER Hendi Santika "hendisantika@yahoo.co.id"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]
