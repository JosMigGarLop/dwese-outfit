# Utilizar una imagen de JDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el código fuente completo al contenedor
COPY . .

# Copiar el archivo de entorno específico al contenedor
COPY .env.docker .env

# Construir y empaquetar la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto por defecto de Spring Boot (8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["./mvnw", "spring-boot:run"]
