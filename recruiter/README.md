# ECE366-RecruitingPlatform

This is an online Recruiting platform for ECE366 Software Engineering.

Project by Liao Hu, Omar Thenmalai

## Usage
### Tech Stack

- Maven, Spring

This project uses the `Maven` project manager and `Spring` as the framework.

To run the project, simply clone the project to a local directory and load the project with `IntelliJ IDEA`.

The server will launch on port 8080 (given that port 8080 is free).

### Hardcoding Data

Some sample data have been hardcoded in the program, located in files in the `Store` folder.

### cURL Code for APIs Testing

Here are some sample cURL code for APIs testing. Not all APIs are included. More available APIs can be found in files in the `util` folder.


- Create an account
```
curl --location --request POST 'localhost:8080/users/createAccount?password=password&email=test2@gmail.com&firstName=fn1&lastName=ln1&accountType=1'
```

- Login
```
curl --location request POST 'localhost:8080/users/login?password=password&email=test@gmail.com'
```

- Get all accounts
```
curl --location --request GET 'localhost:8080/users/getAccounts'
```

- Get open jobs
```
curl --location --request GET 'localhost:8080/jobs/getOpenJobs'
```


- Get an application status
```
curl --location --request GET 'localhost:8080/applications/getOpenApplications?jobId=1&userId=1'
```
