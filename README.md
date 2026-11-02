# springboot-docker-buildpacks

[![Java CI with Maven](https://github.com/hendisantika/springboot-docker-buildpacks/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/springboot-docker-buildpacks/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-6DB33F?logo=springboot)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

Sample Spring Boot application showing several ways to package a Spring Boot app as a container image:

1. Plain `Dockerfile` (fat JAR)
2. Multi-stage layered `Dockerfile2`
3. Cloud Native Buildpacks via `spring-boot:build-image` (no Dockerfile)
4. Google Jib via `jib-maven-plugin` (no Dockerfile, no Docker daemon needed)

## Tech Stack

| Component     | Version                                    |
|---------------|--------------------------------------------|
| Java          | 25 (LTS)                                   |
| Spring Boot   | 4.1.x (Web MVC, Actuator)                  |
| Lombok        | managed by Spring Boot                     |
| Build tool    | Maven (wrapper included)                   |
| Buildpacks    | Paketo (Amazon Corretto, `BP_JVM_VERSION=25`) |
| Jib           | 3.5.x                                      |

## Prerequisites

- JDK 25 (e.g. Temurin or Amazon Corretto)
- Maven 3.9+ (or use `./mvnw`)
- Docker (for Dockerfile & Buildpacks builds)

## Things todo list

1. Clone this repository: `git clone https://github.com/hendisantika/springboot-docker-buildpacks.git`
2. Go inside the folder: `cd springboot-docker-buildpacks`
3. Package the application:
   ```shell
   mvn clean package
   ```
4. Run locally:
   ```shell
   java -jar target/springboot-docker-buildpacks-0.0.1-SNAPSHOT.jar
   ```

### 1. Build with `Dockerfile`

Uses `amazoncorretto:25` as base image and copies the fat JAR:

```shell
docker build -t springboot-docker-buildpacks:v1 .
docker run -p 8080:8080 springboot-docker-buildpacks:v1
```

### 2. Build with layered `Dockerfile2`

Multi-stage build on `eclipse-temurin:25-jre`. The first stage extracts the JAR layers using Spring Boot's
`jarmode=tools`, the second stage copies each layer separately so dependencies are cached between builds:

```shell
docker build -f Dockerfile2 -t springboot-docker-buildpacks:layered .
docker run -p 8080:8080 springboot-docker-buildpacks:layered
```

Inspect the layers inside the image with [dive](https://github.com/wagoodman/dive):

```shell
dive springboot-docker-buildpacks:layered
```

### 3. Build with Cloud Native Buildpacks (Spring Boot plugin)

The Spring Boot Maven plugin creates OCI images from source using Paketo Buildpacks — no Dockerfile required.
The image is configured in `pom.xml`:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <image>
            <buildpacks>
                <buildpack>gcr.io/paketo-buildpacks/amazon-corretto:latest</buildpack>
                <buildpack>gcr.io/paketo-buildpacks/ca-certificates:latest</buildpack>
                <buildpack>gcr.io/paketo-buildpacks/java:latest</buildpack>
            </buildpacks>
            <name>docker.io/hendisantika/${project.artifactId}:${project.version}</name>
            <env>
                <BP_JVM_VERSION>${java.version}</BP_JVM_VERSION>
            </env>
        </image>
    </configuration>
</plugin>
```

Build and run the image:

```shell
mvn spring-boot:build-image
docker images
docker run -p 8080:8080 hendisantika/springboot-docker-buildpacks:0.0.1-SNAPSHOT
```

### 4. Build with Jib

[Jib](https://github.com/GoogleContainerTools/jib) is an image builder plugin from Google that builds an optimized
image directly from Maven:

```xml
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>3.5.2</version>
</plugin>
```

```shell
# push directly to a registry (no Docker daemon needed)
mvn compile jib:build -Dimage=<docker registry name>/springboot-docker-buildpacks:v1

# or build into the local Docker daemon
mvn compile jib:dockerBuild -Djib.from.image=eclipse-temurin:25-jre -Dimage=springboot-docker-buildpacks:jib
```

### 5. Try it

Open your favorite browser: http://localhost:8080/actuator

## Continuous Integration

GitHub Actions workflow [`.github/workflows/maven.yml`](.github/workflows/maven.yml):

- Runs on push & pull request to `master` (and manually via `workflow_dispatch`)
- Builds and tests with Temurin JDK 25 (`mvn clean verify`) and uploads the JAR as an artifact
- Submits the Maven dependency graph on push to `master` (non-blocking; enable
  **Settings → Code security → Dependency graph** for it to succeed)

## Dependabot

[`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies up to date:

| Ecosystem      | Schedule              | Notes                                                        |
|----------------|-----------------------|--------------------------------------------------------------|
| Maven          | daily 21:00 WIB       | Grouped: `spring-boot`, `maven-plugins`, `lombok`; Boot majors ignored |
| GitHub Actions | weekly (Mon) 21:00 WIB | All actions grouped in one PR                                |
| Docker         | weekly (Mon) 21:00 WIB | Base images in `Dockerfile*`; JDK major bumps ignored        |

Source [article link](https://reflectoring.io/spring-boot-docker/).
