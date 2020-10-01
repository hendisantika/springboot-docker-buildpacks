# springboot-docker-buildpacks
## Things todo list
1. Clone this repository: `git clone https://github.com/hendisantika/springboot-docker-buildpacks.git`
2. Go inside the folder: `cd springboot-docker-buildpacks`
3. Package application: `mvn clean package`
4. Build App using Dockerfile: `docker images`
    ```docker
    docker build  -t usersignup:v1 .
    
    [+] Building 21.3s (7/7) FINISHED                                                                                                                                           
     => [internal] load build definition from Dockerfile                                                                                                                   0.1s
     => => transferring dockerfile: 251B                                                                                                                                   0.0s
     => [internal] load .dockerignore                                                                                                                                      0.0s
     => => transferring context: 2B                                                                                                                                        0.0s
     => [internal] load metadata for docker.io/adoptopenjdk/openjdk11:alpine-jre                                                                                           4.2s
     => [1/2] FROM docker.io/adoptopenjdk/openjdk11:alpine-jre@sha256:3706d705804e2149a9c876ed7aa432f3cb6dfb06061d237f948a185158c71a4c                                    16.1s
     => => resolve docker.io/adoptopenjdk/openjdk11:alpine-jre@sha256:3706d705804e2149a9c876ed7aa432f3cb6dfb06061d237f948a185158c71a4c                                     0.0s
     => => sha256:3706d705804e2149a9c876ed7aa432f3cb6dfb06061d237f948a185158c71a4c 433B / 433B                                                                             0.0s
     => => sha256:4f3b15c98c92b942b8b548c5288797ca90e64d58d1de476c8015576f4ffc1e0f 951B / 951B                                                                             0.0s
     => => sha256:a7b99112d065c7530e1de192007adc341e73955167fe8540dc22c327287ca9eb 7.26kB / 7.26kB                                                                         0.0s
     => => sha256:ffa7158a1780abe48cc7c41a1697b4b021b5776220532eca21791266ffa5b860 6.39MB / 6.39MB                                                                         3.5s
     => => sha256:dce2455ca1013d7f6ec0368cfac1182a0e6a93ee956f7a01342d0c514147478f 43.76MB / 43.76MB                                                                      14.4s
     => => extracting sha256:ffa7158a1780abe48cc7c41a1697b4b021b5776220532eca21791266ffa5b860                                                                              0.4s
     => => extracting sha256:dce2455ca1013d7f6ec0368cfac1182a0e6a93ee956f7a01342d0c514147478f                                                                              1.3s
     => [internal] load build context                                                                                                                                      1.4s
     => => transferring context: 20.28MB                                                                                                                                   1.4s
     => [2/2] COPY target/*.jar application.jar                                                                                                                            0.9s
     => exporting to image                                                                                                                                                 0.1s
     => => exporting layers                                                                                                                                                0.1s
     => => writing image sha256:13e8f371537326a103719e228ac32c6f8e39f1e064bcfcd0097f6f42979fe823                                                                           0.0s
     => => naming to docker.io/library/usersignup:v1 
    ```
5. Check Docker Images
    ```docker
    docker images 
    
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    usersignup          v1                  13e8f3715373        22 seconds ago      169MB
    alpine/git          latest              94f8849864da        8 days ago          28.4MB
    ```
6. Viewing the Layers Inside the Container Image
Let us see the stack of layers inside the image. We will use the [dive](https://github.com/wagoodman/dive) tool to view those layers:
    ```shell script
    │ Layers ├─────────────────────────────────────────────────────────────────────────── ┃ ● Current Layer Contents ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Cmp   Size  Command                                                                   Permission     UID:GID       Size  Filetree
        5.6 MB  FROM 31609b718dd2bed                                                      -rw-r--r--         0:0      20 MB  ├── application.jar
         14 MB  apk add --no-cache tzdata --virtual .build-deps curl binutils zstd     && drwxr-xr-x         0:0     841 kB  ├── bin                    
        129 MB  set -eux;     apk add --no-cache --virtual .fetch-deps curl;     ARCH="$( -rwxrwxrwx         0:0        0 B  │   ├── arch → /bin/busybox
         20 MB  COPY target/*.jar application.jar # buildkit                              -rwxrwxrwx         0:0        0 B  │   ├── ash → /bin/busybox   
                                                                                          -rwxrwxrwx         0:0        0 B  │   ├── base64 → /bin/busybox  
    │ Layer Details ├──────────────────────────────────────────────────────────────────── -rwxrwxrwx         0:0        0 B  │   ├── bbconfig → /bin/busybox
                                                                                          -rwxr-xr-x         0:0     841 kB  │   ├── busybox           
    Tags:   (unavailable)                                                                 -rwxrwxrwx         0:0        0 B  │   ├── cat → /bin/busybox  
    Id:     aeea6b61a974e24c959ce5c288c930a985e0acb4f8b32aa703f610d2a9d0fffd              -rwxrwxrwx         0:0        0 B  │   ├── chgrp → /bin/busybox
    Digest: sha256:ccdb1befa51aa150597d1d5ba127863d2858a46f60212416c127508aca8e27ee       -rwxrwxrwx         0:0        0 B  │   ├── chmod → /bin/busybox
    Command:                                                                              -rwxrwxrwx         0:0        0 B  │   ├── chown → /bin/busybox 
    COPY target/*.jar application.jar # buildkit                                          -rwxrwxrwx         0:0        0 B  │   ├── conspy → /bin/busybox
                                                                                          -rwxrwxrwx         0:0        0 B  │   ├── cp → /bin/busybox  
    │ Image Details ├──────────────────────────────────────────────────────────────────── -rwxrwxrwx         0:0        0 B  │   ├── date → /bin/busybox
    ▏^C Quit ▏Tab Switch view ▏^F Filter ▏Space Collapse dir ▏^Space Collapse all dir ▏^A Added ▏^R Removed ▏^M Modified ▏^U Unmodified ▏^B Attributes ▏                        
    ```
 7. Using the Spring Boot Plugin
    The Spring Boot plugin creates OCI images from the source code using a Buildpack. Images are built using the `bootBuildImage` task (Gradle) or the `spring-boot:build-image` goal (Maven) and a local Docker installation.
 
    We can customize the name of the image required for pushing to the Docker Registry by specifying the name in the image tag:
    ```shell script
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <image>
          <name>docker.io/hendisantika/${project.artifactId}:v1</name>
        </image>
      </configuration>
    </plugin>
    ```
    
    Let us use Maven to run the `build-image` goal to build the application and create the container image. We are not using any Docker file now.
    ```shell script
    mvn spring-boot:build-image
    ```
    
    From the output, we can see the paketo Cloud-Native buildpack being used to build a runnable OCI image. As we did earlier, we can see the image listed as a Docker image by running the command:
    ```docker
    docker images 
    
    Hendis-MacBook-Pro:springboot-docker-buildpacks hendisantika$ docker images 
    REPOSITORY                                  TAG                     IMAGE ID            CREATED             SIZE
    usersignup                                  v1                      13e8f3715373        13 minutes ago      169MB
    paketobuildpacks/run                        base-cnb                b4873a43e158        4 days ago          83.3MB
    alpine/git                                  latest                  94f8849864da        8 days ago          28.4MB
    gcr.io/paketo-buildpacks/builder            base-platform-api-0.3   35ac1eee07a7        40 years ago        666MB
    hendisantika/springboot-docker-buildpacks   v1                      9e48ca5913e3        40 years ago        256MB
    Hendis-MacBook-Pro:springboot-docker-buildpacks hendisantika$ 

    ```
8. Building a Container Image with Jib
    Jib is an image builder plugin from Google and provides an alternate method of building a container image from source code.

    We configure the jib-maven-plugin in pom.xml:
    ```shell script
    <plugin>
        <groupId>com.google.cloud.tools</groupId>
        <artifactId>jib-maven-plugin</artifactId>
        <version>2.5.2</version>
   </plugin>
    ```
   
   Next, we trigger the Jib plugin with the Maven command to build the application and create the container image. As before, we are not using any Docker file here:
   ```docker
    mvn compile jib:build -Dimage=<docker registry name>/usersignup:v1
    ```