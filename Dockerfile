# Etapa 1: Construcción con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera con JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto del microservicio Pacientes
EXPOSE 8081

# Variables para Consul
ENV CONSUL_HOST=consul
ENV CONSUL_PORT=8500

ENTRYPOINT ["java", "-jar", "app.jar"]