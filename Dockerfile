# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace/app

# Copia arquivos do projeto
COPY pom.xml .
COPY src src

# Build do projeto (skip tests para acelerar)
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o jar gerado no build
COPY --from=build /workspace/app/target/*.jar app.jar

# Expõe porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
