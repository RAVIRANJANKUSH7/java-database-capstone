FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY app/ .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/smart-clinic-management-system.jar"]
