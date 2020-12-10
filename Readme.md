![Spring Logo](https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/800px-Spring_Framework_Logo_2018.svg.png)
![React Logo](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/320px-React-icon.svg.png)

# SpringLeaves

SpringLeaves is a Trello-like application. It has Boards, Issues, Milestones, and multiple features for project management.

## Project Layout

Application: React.js project, the App itself.
Service: Spring Boot Rest API, the data access layer.
Docs: Documentation related to the data structure and access rights.


## Get Started on development

Clone the Project.
Go to the Application folder, and run

`cd jav5 && npm install`

create a .env file at the root of the project, copy-paste the values in .env.dist and modify them to suit your needs, then run

`npm run start`

to run the project on port 3000, in development mode.

Import the Maven project into your favorite Java IDE (project is located in Service/quest-spring-boot).
Simply run it from the IDE, it will listen on port 8090.

API docuementation is located at http://localhost:8090/swagger-ui

Your SpringLeaves project stack is now up and running !