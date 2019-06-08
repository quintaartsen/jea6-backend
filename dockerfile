FROM maven:3.6.0-jdk-8-alpine  as BUILD
WORKDIR /opt
COPY [ "src", "./src" ]
COPY [ "pom.xml", "./" ]
RUN mvn -B -DskipTests clean package

FROM maven:3.6.0-jdk-8-alpine  as TEST
RUN mvn -B clean package

FROM openjdk:8-jdk-alpine as FINAL
WORKDIR /app
COPY --from=build /opt/target/*.jar ./twitter.jar
ENTRYPOINT ["java","-jar","twitter.jar"]