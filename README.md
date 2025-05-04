![Mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=black)
![JavaSpring](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=black)

# About Book Loan API
Book Loan API is the first project that I make. Made with Java Spring this API has book management and loan methods,
users authentication with JWT and connection with database Mysql.

## Technologies used
Docker 2.4
Java 21.0.1
Maven 4.0


# Methods

## Book Controller
### Method Save
![PUT](https://img.shields.io/badge/HTTP-POST-default)  /api/v1/books/save  
#### Parameters
No parameters required

### Request Body ![Required ](https://img.shields.io/badge/-Required-red?style=flat&logo=probot&logoColor=white)
 Application/Json

### Request body example
``` 
{
  "title": "string",
  "author": "string",
  "isbn": "1934919771046"
}

```
### Responses

#### Code | Description
200 | OK
#### Media Type
 */ *
##### Controls Accept Header

### Response example
``` 
{
  "id": 9007199254740991,
  "title": "string",
  "author": "string",
  "isbn": "8502576017943"
}

```

### Method Update
![PUT](https://img.shields.io/badge/HTTP-PUT-yellow)  /api/v1/books/update
### Parameters
No parameters required

### Request Body ![Required ](https://img.shields.io/badge/-Required-red?style=flat&logo=probot&logoColor=white)
Application/Json

### Request body example
``` 
{
  "id": 9007199254740991,
  "title": "string",
  "author": "string",
  "isbn": "8502576017943"
}
```
### Responses

#### Code | Description
200 | OK