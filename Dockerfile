FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar
RUN ["chmod", "+x", "application.jar"]
ENTRYPOINT ["java", "-jar", "application.jar"]