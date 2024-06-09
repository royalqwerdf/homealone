FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app






# JAR 파일 경로 설정
ARG JAR_FILE=build/libs/*.jar

# 빌드된 JAR 파일 복사
COPY ${JAR_FILE} app.jar

# room.json 파일 복사
COPY src/main/resources/Room.json /app/src/main/resources/Room.json

# 실행 명령어 설정
ENTRYPOINT ["java", "-Dspring.profiles.active=test","-jar", "app.jar"]
