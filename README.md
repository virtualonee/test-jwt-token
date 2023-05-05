# Request List example

## Registration

#### Request
POST http://localhost:8080/auth/registration

#### Body
{
  "username": "user1",
  "age": 20
  "password": "4332asdas32"
}

#### Response
{
    "jwt-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJ2aXJ0dSIsImV4cCI6MTY4MzI1NzM2MSwiaWF0IjoxNjgzMjQ2NTYxLCJ1c2VybmFtZSI6Im93bmVyIn0.vIinZTvGYpTS7X-b73BTfawx0o5QBGSmOBnMSyv30uY"
}

## Authentication

#### Request
POST http://localhost:8080/auth/login

#### Body
{
  "username": "user1",
  "password": "4332asdas32"
}

#### Response
{
    "jwt-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJ2aXJ0dSIsImV4cCI6MTY4MzI1NzM2MSwiaWF0IjoxNjgzMjQ2NTYxLCJ1c2VybmFtZSI6Im93bmVyIn0.vIinZTvGYpTS7X-b73BTfawx0o5QBGSmOBnMSyv30uY"
}

> You need to add "Authorization" key in header with value "Bearer jwt-token" for all requests except registration and Authentication. Token is valid for 3 hours

## CRUD people

### GET ALL

#### Request
GET http://localhost:8080/people

#### Response

[
    {
        "username": "owner",
        "age": 15
    },
    {
        "username": "user1",
        "age": 20
    },
    {
        "username": "user2",
        "age": 20
    },
    {
        "username": "user3",
        "age": 20
    }
]

### GET ONE

#### Request
GET http://localhost:8080/people/{id}/get

#### Response
{
    "username": "user1",
    "age": 20
}

### UPDATE

#### Request
PUT http://localhost:8080/people/{id}/update

#### Body
{
    "username":"newUsername",
    "age": 80
}

#### Response
"OK"

### DELETE

#### Request
DELETE http://localhost:8080/people/{id}/delete

#### Response
"OK"

## CRUD houses

### GET ALL

#### Request
GET http://localhost:8080/houses

#### Response
[
    {
        "id": 1,
        "address": "newAdress",
        "owner": {
            "id": 1,
            "username": "owner",
            "age": 15
        },
        "personDTOList": [
            {
                "username": "user3",
                "age": 20
            }
        ]
    },
    {
        "id": 2,
        "address": "newAdress",
        "owner": {
            "id": 1,
            "username": "owner",
            "age": 15
        },
        "personDTOList": []
    }
]

### GET ONE

#### Request
GET http://localhost:8080/houses/{id}/get

#### Response
{
    "id": 1,
    "address": "newAdress",
    "owner": {
        "id": 1,
        "username": "owner",
        "age": 15
    },
    "personDTOList": [
        {
            "username": "user3",
            "age": 20
        }
    ]
}

### CREATE
#### Request
POST http://localhost:8080/houses/add

### Body
{
    "address": "newAdress12"
}

#### Response 
"CREATED"

### UPDATE
#### Request
POST http://localhost:8080/houses/{id}/update

#### Body
{
    "address": "some address"
}

#### Response 
"OK"

### DELETE
#### Request
DELETE http://localhost:8080/houses/{id}/delete

#### Response 
"OK"

## House's occupant logic
### ADD OCCUPANT TO HOUSE
#### Request 
POST http://localhost:8080/houses/{id}/add_occupant

#### Body
{
    "username": "user2"
}
> User with this name must exist

#### Response 
"OK"

### DELETE ALL OCCUPANT FROM HOUSE
#### Request 
DELETE http://localhost:8080/houses/{id}/delete_occupants

#### Response 
"OK"
