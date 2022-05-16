FROM openjdk:11
LABEL maintainer="assignment.getir.com"
ADD target/ReadinIsGood-0.0.1-SNAPSHOT.jar emin-karaca-getir-assignment.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "emin-karaca-getir-assignment.jar"]