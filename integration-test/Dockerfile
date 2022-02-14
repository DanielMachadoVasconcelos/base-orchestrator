FROM openjdk:17
WORKDIR /
ADD build/distributions/integration-tests.tar /

RUN javac IntegrationTestRunner.java
CMD ["java", "IntegrationTestRunner"]