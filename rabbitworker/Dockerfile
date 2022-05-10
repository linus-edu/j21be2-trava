FROM eclipse-temurin:17 AS build-stage

WORKDIR /build

# Copy gradle wrapper and trigger gradle to be downloaded.
COPY ./gradle/ ./gradle/
COPY ./gradlew .
COPY ./settings.gradle.kts .
RUN ./gradlew --version --no-daemon

# Trigger gradle to download project dependencies so that they
# can be cached when building without any dependency changes.
COPY ./build.gradle.kts .
RUN ./gradlew dependencies --no-daemon

# Build the actual application.
COPY ./src/ ./src/
RUN ./gradlew build --no-daemon
# Gradle will produce two jar files, remove the one we don't want
RUN rm /build/build/libs/*-plain.jar

# Create a fresh minimal image and copy the build project into it.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build-stage /build/build/libs/rabbitworker-*.jar /app/rabbitworker.jar
EXPOSE 8080
CMD ["java", "-jar", "rabbitworker.jar", "-Djava.net.preferIPv4Stack=true"]
