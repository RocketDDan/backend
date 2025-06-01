# Liberica JRE 21 사용 (운영 환경에 적합)
FROM bellsoft/liberica-alpine:21-jre

# 작업 디렉토리
WORKDIR /app

# 빌드된 JAR 복사
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]