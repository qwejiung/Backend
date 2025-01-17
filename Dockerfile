# 1단계: Gradle Wrapper를 이용해 빌드
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . /app
# 아래 명령에서 gradle 대신 ./gradlew를 써도 되지만,
# gradle 이미지 안에는 gradle가 있으니 상관없음
RUN gradle clean build -x test

# 2단계: 실제 실행 환경 (이미지 작게 만들기 위해 OpenJDK alpine 등 사용)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# 빌드 산출물(jar)만 복사
COPY --from=build /app/build/libs/LittlePet-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]