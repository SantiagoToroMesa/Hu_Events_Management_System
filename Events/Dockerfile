# ETAPA 1: BUILD (Compilación)
# Usar la etiqueta oficial y estable de Maven con JDK 21 (Temurin + Alpine)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml)
COPY pom.xml .

# Descarga las dependencias
RUN mvn dependency:go-offline -B

# Copia el código fuente completo del proyecto
COPY src /app/src

# Compila y empaqueta la aplicación
RUN mvn package -DskipTests

# -----------------------------------------------

# ETAPA 2: RUN (Ejecución)
# Usar la imagen ligera de JRE Temurin (la etiqueta funcional)
FROM eclipse-temurin:21-jre-alpine

# Crea un usuario de aplicación no root para seguridad
RUN addgroup --system spring && adduser --system --no-create-home --ingroup spring appuser
USER appuser

# Define el puerto que la aplicación escuchará
EXPOSE 8080

# Copia el JAR compilado de la etapa 'build' al contenedor final
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]