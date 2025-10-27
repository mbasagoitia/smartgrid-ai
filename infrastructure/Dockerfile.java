
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /build
COPY backend-java /build
RUN mvn -f pom.xml -DskipTests package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
