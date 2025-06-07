FROM openjdk:24-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:24-jdk-slim
WORKDIR /app
RUN groupadd --system springgroup && useradd --system --gid springgroup springuser
USER springuser
COPY --from=build /app/target/*.jar app.jar
EXPOSE 3003
ENTRYPOINT ["java", "-jar", "app.jar"]