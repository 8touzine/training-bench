FROM maven:3.9.11-eclipse-temurin-17 AS builder
WORKDIR /API
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#pas besoin de jdk
FROM eclipse-temurin:17-jre 
WORKDIR /API
COPY --from=builder /API/target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]