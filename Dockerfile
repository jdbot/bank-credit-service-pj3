FROM openjdk:11
VOLUME /tmp
ADD ./target/bank-credit-service-0.0.1-SNAPSHOT.jar bank-credit.jar
ENTRYPOINT ["java","-jar","bank-credit.jar"]