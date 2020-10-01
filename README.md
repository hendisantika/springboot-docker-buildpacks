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
Let us see the stack of layers inside the image. We will use the dive tool to view those layers: