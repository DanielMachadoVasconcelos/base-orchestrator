FROM openjdk:17
WORKDIR /

COPY build/libs/integration-test.jar /integration-test.jar
CMD java ${JAVA_ENV_VARS} -jar /integration-test.jar

EXPOSE 8011
EXPOSE 9011