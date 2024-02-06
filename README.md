<h1 align="center">🧡 Orange Portfolio 🧡 </h1>

<p1 align="center">

[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/fcmara-hackathon-squad-08/orange-portfolio-backend/blob/dev/README.pt-br)
[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/fcmara-hackathon-squad-08/orange-portfolio-backend/blob/dev/README.md)

</p>

## Technologies 🚀

### This project was developed with the following technologies

- Java
- Spring Boot
- PostgreSQL
- Flyway
- Docker
- Render (Deploy)
- AWS (S3)

## Table of Contents 🛰

- [About 📖](#about-)
- [Endpoints 🛠✨](#endpoints-)
- [Team 👨‍💻](#team-)
- [Run project 💼](#runproject-)
- [Contact 💼](#contact-)


## About 📖

### Description

The Orange Portfolio is a web app that allows users to share their projects online and see other people's projects as well.

### Objective

This project was developed as a challenge in the FCAMARA's recruitment process hackathon, which occurred between 01/22/2024 and 05/02/2024.  


## Endpoints 🛠✨
- API Documentation:
<a href="https://sq8-orange-fcamra.onrender.com/swagger-ui/index.html" rel="noopener noreferrer" target="_blank"> <img align="center" src="https://github.com/Thimachal/api-front-angular/assets/63027260/6324d49d-e87c-425a-ae3a-106514a79d2f" height="80" width="90"/>
- Register:
- *POST*```/user/register``` -> User register
- Authentication:
  
    - *POST*```/auth/login``` -> User authentication

> [!WARNING]
> All operations in the points below are necessary for the user to be logged in

- User
  - *GET*```/user/me``` -> get user   
  - *PUT*```/user/me``` -> update user
    
- Projects:
  - *POST*```/project/add``` -> add a project
  - *GET*```/project/list/tags``` -> get all projects by tag or all projects without specific tag
  - *GET*```/project/list/tags/user``` -> get all projects by tag or all projects without specific tag of current user
  - *PUT*```/project/{id}``` -> update project by id
  - *DELETE*```/project/{id}``` -> delete a project by id
 
## Run project

- Clone the project 

```
git@github.com:fcmara-hackathon-squad-08/orange-portfolio-backend.git
```

- Use your favorite IDE - Intellij, Eclipse...

- Run Docker

```
docker compose up -d
```
- Change in application.yml from prod to dev

- You need to create a AWS Bucket

## Team 👨‍💻

| <a href="https://github.com/stelianok" target="_blank">**Kauã Steliano**</a> | <a href="https://github.com/stelianok" target="_blank">**Thiago Oliveira**</a>
| :---: |:---:|
| [![Kauã](https://github.com/stelianok.png)](https://github.com/stelianok)   | [![Thiago](https://github.com/Thimachal.png)](https://github.com/Thimachal)

| <a href="https://github.com/EricklisCruz" target="_blank">**Ericklis Cruz**</a> | <a href="https://github.com/alanvargas04" target="_blank">**Alan Vargas Silva**</a>
| :---: |:---:|
| [![Ericklis](https://github.com/EricklisCruz.png)](https://github.com/EricklisCruz) |[![Wanderson](https://github.com/alanvargas04.png)](https://github.com/alanvargas04)

---

## Contact 💼

### collaborator 1

LinkedIn: [Kauã Alexandro Steliano](https://www.linkedin.com/in/kauã-steliano-107620181/)

E-mail: stelianok@gmail.com

Github: [Stelianok](https://github.com/stelianok)

### collaborator 2

LinkedIn: [Thiago Oliveira](https://www.linkedin.com/in/thiago-oliveira-tmo/)

E-mail: thimachal@gmail.com

Github: [Thiago Oliveira](https://github.com/Thimachal)

### collaborator 3

LinkedIn: [Ericklis Cruz](https://www.linkedin.com/in/ericklis-cruz/)

E-mail: erickliscruz@gmail.com

Github: [Ericklis Cruz](erickliscruz@gmail.com)

### collaborator 4

LinkedIn: [Alan Vargas Silva](https://www.linkedin.com/in/alan-vargas-37b09b297/)

E-mail: alanvargasenf@gmail.com

Github: [Alan Vargas Silva](https://github.com/alanvargas04)
