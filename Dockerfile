# -------- Build Stage --------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# -------- Run Stage --------
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/jwt-demo-1.0.0.jar app.jar

EXPOSE 8071

CMD ["java", "-jar", "app.jar"]