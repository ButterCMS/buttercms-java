# Stage 1: Build Java SDK and Demo
FROM openjdk:8-jdk-alpine AS build_java

WORKDIR /src
COPY . .
RUN ./mvnw clean install -DskipTests
RUN ./mvnw -f /src/Demo/pom.xml clean package -DskipTests

# Stage 2: Run the Demo
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build_java /src/Demo/target/buttercms-demo-1.0.0-jar-with-dependencies.jar .

ENV API_BASE_URL=https://api.buttercms.com/v2
ENV API_KEY=your_api_key

CMD ["java", "-jar", "buttercms-demo-1.0.0-jar-with-dependencies.jar"]