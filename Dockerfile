# 1) Gradle 빌드용 이미지
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . /app
RUN ./gradlew clean build -x test

# 2) 실제 실행용 이미지
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/build/libs/UMC-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]