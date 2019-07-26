# Widget Quality Control

This is a service to run quality check on logs of a manufacturing unit for classification of widgets based on reference criteria mentioned in the log file.

## Getting Started

The project can be downloaded or cloned from https://github.com/raghav2008gupta/widget-quality-control.git
You will need JDK 8 or above and docker daemon to run this project.
This project is developed with IntelliJ IDEA 2019.1.3

## Prerequisites
JDK8 is required to get a production build of the web service and docker daemon is required to deploy and run it.
Depending on the platform, you can install the prerequisites by following the procedures given at-

#### Git
  - https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
#### JDK
  - http://openjdk.java.net/install/
  - https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
#### Docker
  - https://docs.docker.com/install/

## Installation
Clone or download the repository from https://github.com/raghav2008gupta/widget-quality-control.git

#### Build Process
Build the web service by running the following command in the project folder-

`./gradlew build`

#### Running the tests
To execute the test cases run the following command in the project folder-
    
`./gradlew test`

#### Deployment
- Using JAR file
  
  Deploy the web service using the JAR file in the `build/libs` directory-
  - `java -jar build/libs/365-Widgets.jar`

- Using docker image
  
  Deploy the webservice as a container using docker image-  
  - `docker run --rm -d -p 443:443 -p 9000:9000 365-widgets:0.0.1`
    
- Optional arguments
  
  Following optional arguments can be passed using `-D` when deploying jar or `-e` when deploying docker container-
  
  | Application Properties | Environment Variables |
  |------------------------|-----------------------|
  | app.user.name          | APP_USER_NAME         |
  | app.user.password      | APP_USER_PASSWORD     |
  | app.admin.name         | APP_ADMIN_NAME        |
  | app.admin.password     | APP_ADMIN_PASSWORD    |
  
  App creds are used to access the service endpoints while admin creds are used to access monitoring endpoints.

## Using the Web Service

This web service can be reached at `https://<hostname>:443/` where hostname is `localhost` or the address of the proxy in front of the containers.

Service uses a self signed cert for TLS. Clients need to trust this certificate to use the service.

Following endpoints are available via this service- 
#### Check Quality

Runs quality check on a log file to classify widgets.
The file should be accessible to the service to read from the file path.
 
	resource        - /check-quality
	headers         - Content-Type: text/plain
	request content - file path
	return          - 200 ok & JSON object
	example
		request     
            curl -X "POST" "https://localhost/check-quality" \
            
                -H 'Content-Type: text/plain' \
            
                -u 'user:password' \
            
                -d "https://raw.githubusercontent.com/raghav2008gupta/widget-quality-control/master/src/test/resources/com.widget.quality.control/log1"
		response      
            {
                "hum-1": "keep",
                "mon-1": "keep",
                "temp-1": "precise",
                "hum-2": "discard",
                "mon-2": "discard",
                "temp-2": "ultra precise"
            }
#### Monitoring Endpoints

Production ready endpoints as described at https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html

## Built With

- [Java](https://www.oracle.com/technetwork/java/index.html) - Programming Language 
- [Spring](https://spring.io/) - Web Framework
- [Gradle](https://gradle.org/) - Dependency Management and Build tool
- [Docker](https://www.docker.com/) - Deployment tool

## Authors

- **Raghav Gupta** - (https://github.com/raghav2008gupta)

## Acknowledgments

- Java, Spring, Gradle and Docker official documentation