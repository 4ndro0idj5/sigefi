# ================================
# STAGE 1 — BUILD
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas arquivos necessários para cache eficiente
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests


# ================================
# STAGE 2 — RUNTIME
# ================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/*.jar app.jar

# Porta padrão Spring Boot
EXPOSE 8080

# Executa aplicação
ENTRYPOINT ["java","-jar","app.jar"]
