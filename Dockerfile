# ---- Stage 1: Build ----
FROM maven:3.9-eclipse-temurin-24-noble AS build
WORKDIR /app

# Copiar descriptores de dependencias primero para aprovechar la caché de capas
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B -q

# Copiar el resto del código fuente y compilar
COPY src ./src
RUN ./mvnw package -DskipTests -B -q

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:24-jre-noble AS runtime
WORKDIR /app

# Usuario no-root por seguridad
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx400m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
