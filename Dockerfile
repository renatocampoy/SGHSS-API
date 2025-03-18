# Usa a imagem do Maven para compilar o projeto
FROM maven:3.9-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o código-fonte para dentro do contêiner
COPY . .

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Usa uma imagem do OpenJDK para rodar o JAR
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR compilado do estágio anterior para o contêiner
COPY --from=builder /app/target/sghss-backend.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8088

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]