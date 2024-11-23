# Spring Boot 3.3.5 Application

This project is a Spring Boot application built with Java 21. 
It includes detailed instructions to run, build, and deploy the application.

---

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Building the Application](#building-the-application)
- [Building a Docker Image](#building-a-docker-image)
- [Deploying the Application](#deploying-the-application)

---

## Requirements

Ensure you have the following installed on your system:

- **Java 21**
- **Maven** (version 3.9.0 or later)
- **Docker**

---

## Installation

1. Clone the repository:

    ```bash
    git clone <repository-url>
    cd <repository-folder>
    ```

2. Verify the Maven dependencies:

    ```bash
    mvn dependency:resolve
    ```

---

## Running the Application

### Local Development


#### It'll be necessary that docker is installed and running. Since this project uses docker compose file to auto start the mysql database.

Run the application locally with:

```bash 
mvn spring-boot:run
```

Alternatively, you can build and execute the jar file:

```bash
mvn clean package
java -jar target/calculator-challenge-api:0.0.1.jar
```

### Configuration
Modify the configuration properties in src/main/resources/application.properties or create a src/main/resources/application.yml file for custom settings.

### Building the Application
To build the application, run:

```bash
mvn clean package
```
The compiled .jar file will be located in the target/ directory.

### Building a Docker Image
This project includes configuration to use the Maven plugin to create a Docker image. 


Run the Maven command:

```bash
mvn spring-boot:build-image
```
The plugin will create a Docker image using the name and version specified in the pom.xml file.

Verify the image:

```bash
docker images
```
### Running the Docker Container
Run the application inside a Docker container:

```bash
docker run -d -p 8080:8080 <image-name>
```
## Deploying the Application

### Deploy Locally with Docker
Build the Docker image:
```bash
mvn spring-boot:build-image
```
Run the container:

```bash
docker run -d -p 8080:8080 <image-name>
```
### Deploying to a Cloud Platform

Push the Docker image to a registry (e.g., Docker Hub):

```bash
docker tag <image-name> <your-dockerhub-username>/<image-name>:<version>
docker push <your-dockerhub-username>/<image-name>:<version>
```

Use your cloud provider’s deployment tools (e.g., Kubernetes, AWS ECS, Azure App Service, etc.) to deploy the application using the Docker image.


## Deploying All Applications

In order to make it easier to deploy all applications, I provide a docker-compose file example that can be used as a reference.
You can find it in the root directory of the project as `compose-full-application-example.yaml`


## Happy coding! 🎉




