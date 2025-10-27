
FROM maven:3.8.7-openjdk-17 as build
WORKDIR /build
COPY . /build
RUN mvn -f pom.xml -DskipTests package

FROM openjdk:17
WORKDIR /app
COPY --from=build /build/target/backend-java-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
