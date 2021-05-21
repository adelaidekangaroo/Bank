## Users

### Get by id
```

Request

GET http://localhost:8080/bank/rest/profile/users/1 
Content-type: application/json; charset=utf-8 
Authorization: Basic user1 pass

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 09:26:27 GMT
Content-length: 76

Body:

{
  "login": "user1",
  "fullName": "Ivanov Ivan Ivanovich",
  "userType": "INDIVIDUAL"
}

```
### Get by id (Bad request)
```

Request

GET http://localhost:8080/bank/rest/profile/users/5 
Content-type: application/json; charset=utf-8 
Authorization: Basic user1 pass

```
```

Response

HTTP/1.1 400 Bad Request
Date: Fri, 21 May 2021 10:32:20 GMT
Content-length: 0

```
### Create
```
Request

POST http://localhost:8080/bank/rest/admin/users
Authorization: Basic empl1 pass2
Content-type: application/json; charset=utf-8

{
"id": null,
"login": "newuser",
"password": "newpass",
"fullName": "Novopov Novop Novopolzovovich",
"role": "USER",
"userType": "INDIVIDUAL"
}

```
```

Response

HTTP/1.1 201 Created
Date: Fri, 21 May 2021 11:01:22 GMT
Content-length: 0

```
### Create (w/o emplyee role)
```
Request

POST http://localhost:8080/bank/rest/admin/users
Authorization: Basic empl1 pass2
Content-type: application/json; charset=utf-8

{
"id": null,
"login": "newuser",
"password": "newpass",
"fullName": "Novopov Novop Novopolzovovich",
"role": "USER",
"userType": "INDIVIDUAL"
}

```
```

Response

HTTP/1.1 401 Unauthorized
Www-authenticate: Basic realm="myrealm"
Date: Fri, 21 May 2021 10:33:33 GMT
Content-length: 0

```
### Get all counterparties
```

Request

GET http://localhost:8080/bank/rest/profile/users/1?counterparties=true
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 10:29:25 GMT
Content-length: 161

Body:

[
  {
    "login": "user2",
    "fullName": "Semenov Semen Semenovich",
    "userType": "INDIVIDUAL"
  },
  {
    "login": "empl1",
    "fullName": "Romanova Daria Romanovna",
    "userType": "INDIVIDUAL"
  }
]

```
## Accounts

### Get by id
```

Request

GET http://localhost:8080/bank/rest/profile/users/1/accounts/1
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 10:57:41 GMT
Content-length: 33

Body:

{
  "userId": 1,
  "amount": 0.00,
  "id": 1
}

```
### Get all by user
```

Request

GET http://localhost:8080/bank/rest/profile/users/1/accounts
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 10:58:44 GMT
Content-length: 73

Body:

[
  {
    "userId": 1,
    "amount": 0.00,
    "id": 1
  },
  {
    "userId": 1,
    "amount": 10000.00,
    "id": 2
  }
]
```
### Create
```
Request

POST http://localhost:8080/bank/rest/admin/users/1/accounts
Authorization: Basic empl1 pass2
Content-type: application/json; charset=utf-8

{
  "id": null,
  "userId": 1,
  "amount": 5.00
}

```
```

Response

HTTP/1.1 201 Created
Date: Fri, 21 May 2021 11:01:22 GMT
Content-length: 0

```
### Update
```
Request

PUT http://localhost:8080/bank/rest/profile/users/1/accounts/2
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

{
  "id": 2,
  "userId": 1,
  "amount": 5.00
}

```
```

Response

HTTP/1.1 204 No Content
Date: Fri, 21 May 2021 11:09:36 GMT

```
## Cards

### Get by id
```

Request

GET http://localhost:8080/bank/rest/profile/users/1/cards/1
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 11:12:18 GMT
Content-length: 85

Body:

{
  "id": 1,
  "accountId": 1,
  "isActive": false,
  "number": 1111111111111111,
  "isConfirmed": false,
}

```
### Get all by user
```

Request

GET http://localhost:8080/bank/rest/profile/users/1/cards
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 11:13:43 GMT
Content-length: 172

Body:

[
  {
    "accountId": 1,
    "isActive": false,
    "number": 1111111111111111,
    "isConfirmed": false,
    "id": 1
  },
  {
    "accountId": 2,
    "isActive": true,
    "number": 1234567890123456,
    "isConfirmed": false,
    "id": 2
  }
]
`````

### Get all unconfirmed
```

Request

GET http://localhost:8080/bank/rest/profile/users/cards?notconfirmed=true
Authorization: Basic empl1 pass3
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 11:24:31 GMT
Content-length: 342

Body:

[
  {
    "accountId": 1,
    "isActive": false,
    "number": 1111111111111111,
    "isConfirmed": false,
    "id": 1
  },
  {
    "accountId": 2,
    "isActive": true,
    "number": 1234567890123456,
    "isConfirmed": false,
    "id": 2
  },
  {
    "accountId": 3,
    "isActive": true,
    "number": 3456786543211111,
    "isConfirmed": false,
    "id": 3
  },
  {
    "accountId": 4,
    "isActive": true,
    "number": 3456786543666666,
    "isConfirmed": false,
    "id": 4
  }
]
`````
### Create
```
Request

POST http://localhost:8080/bank/rest/profile/users/1/cards
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8


{
  "id": null,
  "accountId": 1,
  "isActive": false,
  "number": 1111111111111112,
  "isConfirmed": false
}
```
```

Response

HTTP/1.1 201 Created
Date: Fri, 21 May 2021 11:15:42 GMT
Content-length: 0

```
### Update
```
Request

PUT http://localhost:8080/bank/rest/admin/users/1/cards/2
Authorization: Basic empl1 pass2
Content-type: application/json; charset=utf-8

{
  "id": 2,
  "accountId": 2,
  "isActive": true,
  "number": 1234567890123456,
  "isConfirmed": true
}

```
```

Response

HTTP/1.1 204 No Content
Date: Fri, 21 May 2021 11:18:43 GMT

```
## Payments

### Get by id
```

Request

GET http://localhost:8080/bank/rest/profile/users/1/payments/1
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 11:20:31 GMT
Content-length: 82

Body:

{
  "id": 1,
  "amount": 100.00,
  "accountOwnerId": 2,
  "counterpartyId": 3,
  "isConfirmed": false
}

```


### Get all unconfirmed
```

Request

GET http://localhost:8080/bank/rest/profile/users/payments?notconfirmed=true
Authorization: Basic empl1 pass3
Content-type: application/json; charset=utf-8

```
```

Response

HTTP/1.1 200 OK
Date: Fri, 21 May 2021 11:26:21 GMT
Content-length: 168

Body:

[
  {
    "amount": 100.00,
    "accountOwnerId": 2,
    "counterpartyId": 3,
    "isConfirmed": false,
    "id": 1
  },
  {
    "amount": 1000.00,
    "accountOwnerId": 3,
    "counterpartyId": 2,
    "isConfirmed": false,
    "id": 2
  }
]


```

### Confirm
```
Request

PUT http://localhost:8080/bank/rest/admin/users/1/payments/1
Authorization: Basic empl1 pass2
Content-type: application/json; charset=utf-8

{
  "id": 1,
  "amount": 100.00,
  "accountOwnerId": 2,
  "counterpartyId": 3,
  "isConfirmed": false
}

```
```

Response

HTTP/1.1 204 No Content
Date: Fri, 21 May 2021 11:28:01 GMT

```

### Create
```
Request

POST http://localhost:8080/bank/rest/profile/users/1/payments
Authorization: Basic user1 pass
Content-type: application/json; charset=utf-8

{
  "id": null,
  "amount": 100.00,
  "accountOwnerId": 2,
  "counterpartyId": 3,
  "isConfirmed": false
}
```
```

Response

HTTP/1.1 201 Created
Date: Fri, 21 May 2021 11:32:54 GMT
Content-length: 0

`````