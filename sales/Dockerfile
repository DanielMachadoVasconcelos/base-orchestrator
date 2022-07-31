FROM openjdk:17
WORKDIR /

COPY build/libs/sales-1.0.0.0-SNAPSHOT.jar /sales.jar

CMD java ${JAVA_ENV_VARS} -jar /sales.jar

EXPOSE 8082
EXPOSE 9082