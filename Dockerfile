FROM coney/serverjre:8

COPY build/libs/user-server-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
