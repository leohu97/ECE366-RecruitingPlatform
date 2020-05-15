# ECE366-RecruitingPlatform

This is an online Recruiting platform for ECE366 Software Engineering.

Project by Liao Hu, Omar Thenmalai

## Usage
### Tech Stack

- Maven, Spring Boot, Spring Security, Spring Data JPA

This project uses the `Maven` project manager and `Spring` as the framework with a front-end build in `ReactJS`.


### Front-end

The Front-end of this project can located in the following repo: https://github.com/omarthenmalai/Recruiting-Platform-Frontend

Ensure that you have React downloaded before attempting to start the front-end application. 

To run the project, simply clone the project to a local directory. Open up a command line and navigate to the folder that contains the cloned repository. Use the following command:
```
npm start
```
to start the application. The application will load on "http://localhost:3000" and can be viewed in the browser. Ensure that the back-end is started on "http://localhost:8080", otherwise the various API calls will fail.

### Back-end

To run the back-end of this project, simply clone this repo to a local directory and load the project with `IntelliJ IDEA`.

Before running, make sure database has been correctly set. To view the default configuration or modify the database source, please go to file `application.properties`  

The server will launch on port 8080 (given that port 8080 is free).

A full list of API can be found in file `API List`
