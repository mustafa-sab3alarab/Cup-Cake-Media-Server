FROM openjdk:11 as builder

COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/cup-cake-media.jar ./cup-cake-media.jar

CMD ["java", "-jar", "cup-cake-media.jar"]