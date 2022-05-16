FROM openjdk:17
WORKDIR /

COPY build/libs/sales-1.0.0.0-SNAPSHOT.jar /sales.jar
COPY startup.sh /startup.sh

#CMD java ${JAVA_ENV_VARS} -jar /sales.jar
CMD ["sh", "-c" , "/startup.sh sales.jar"]

EXPOSE 8081
EXPOSE 9081