FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE $PASSENGER_SERVICE_PORT
ENTRYPOINT ["java", "-jar", "/app/app.jar"]