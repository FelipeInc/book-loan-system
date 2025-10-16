![Mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=black)
![JavaSpring](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=black)
---

# About Book Loan API
Book Loan API is the first project I’ve made. Built with Java Spring, this API includes book management and loan 
features, user authentication with JWT, and a connection to a MySQL database.

---

## Technologies used

#### Docker 2.4
#### Java 21.0.1
#### Maven 4.0
#### SpringBoot 3.4.5

---

## Project Structure
```
src/
├── main/
│   ├── java/book/loan/system/
│   │   ├── controller/
│   │   ├── domain/
│   │   ├── exception/
│   │   ├── repository/
│   │   ├── request/
│   │   ├── security/
│   │   ├── service/
│   │   └── util/
│   └── resources/
│       ├── application.properties
│       └── application.yml/
├── test/
│   ├── java/book/loan/system/
│   │   ├── controller/
│   │   ├── DTO/
│   │   ├── integration/
│   │   ├── repository/
│   │   ├── service/
│   │   └── util/
│   └── resources/
│       └── application.yml/
```

---

## How to Run the Project

Follow these steps to run the project locally:

---

### Prerequisites

Make sure you have installed:
- [Java 21+](https://www.oracle.com/java/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Docker](https://www.docker.com/)

---

###  Clone the repository

```bash

git clone https://github.com/FelipeInc/book-loan-system
cd book-loan-system

```

---

### Create a docker-compose.yml file in the project root (if not present):
```

services:
  db:
    image: mysql
    container_name: library_db
    environment:
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - library_db:/var/lib/mysql

volumes:
  library_db:

```

---

### Start Docker Compose:

```

docker-compose up

```

---

### Run the project in IntelliJ

```

Open IntelliJ IDEA.

Open the cloned project folder.

Let IntelliJ download dependencies (Maven).

Run the main class annotated with @SpringBootApplication.

Usually located in src/main/java/book/loan/system/ApplicationStart.java

```

---

## Swagger documentation
``` 
1- Run the application

2- Go to this web site  http://localhost:8080/swagger-ui/index.html#/ 
```

---

## Developer

#### [![Linkedin](https://i.sstatic.net/gVE0j.png) LinkedIn](https://www.linkedin.com/in/felipe-silva-0643192b0)

#### [![GitHub](https://i.sstatic.net/tskMh.png) GitHub](https://github.com/FelipeInc)



