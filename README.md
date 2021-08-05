# AddendumTask
### Job interview task

This project is for educational purposes only. For Java and Spring Boot coding skills improvement.

Site published at: https://github.com/RimidisM/AddendumTask


### Author: [RimidisM](https://github.com/RimidisM)
### Task owner: ADDENDUM GROUP


# Authentication
For all request must be used Basic Auth! username and password will be provided by email.

#### Responses:
401
```json
{
  "timestamp": "2021-08-03T19:37:26.899418800+03:00[Europe/Helsinki]",
  "type": "Auth",
  "code": "auth-002",
  "error": "Request is missing authentication credentials",
  "detail": "Authentication faild due to missing authentication credentials. Ensure that the username and password included in the request"
}
```
or
```json
{
  "timestamp": "2021-08-03T19:44:22.649833800+03:00[Europe/Helsinki]",
  "type": "Auth",
  "code": "auth-001",
  "error": "Incorrect credentials",
  "detail": "Authentication faild due to incorrect authentication credentials. Ensure that the username and password included in the request are correct"
}
```

# Controller
### /api/addBeneficiary

#### Request:
```json
{
"name": "string"
}
```

#### Responses:
200 (Returns created object)
```json
{
  "id": 0,
  "name": "string",
  "uuid": "string"
}
```

### /api/deleteBeneficiary/{id}

#### Request:
{id} - integer, record id

#### Responses:
200 (Returns deleted object)
```json
{
  "id": 0,
  "name": "string",
  "uuid": "string"
}
```
404
```text
Record not found. Id: 2
```

### /api/getAllBeneficiary

#### Responses:
200 (Returns deleted object)
```json
[
  {
    "id": 2,
    "uuid": "3bee0688-ea9f-4282-9bf4-d430cb0547b1",
    "name": "OneName"
  },
  {
    "id": 3,
    "uuid": "611d5681-3b83-4a0f-af54-287723854180",
    "name": "TwoName"
  }
]
```

### /api/getBeneficiary/{id}

#### Request:
{id} - integer, record id

#### Responses:
200 (Returns one object)
```json
{
  "id": 0,
  "name": "string",
  "uuid": "string"
}
```
404
```text
Record not found. Id: 2
```

### /api/getBeneficiaryQr/{id}

#### Request:
{id} - integer, record id

#### Responses:
200 (Returns QR code with selected object information)

404
```text
Record not found. Id: 2
```

### /api/updateBeneficiary/{id}

#### Request:
{id} - integer, record id

body
```json
{
"name": "string"
}
```

#### Responses:
200 (Returns updated object)
```json
{
  "id": 0,
  "name": "string",
  "uuid": "string"
}
```

:exclamation:
## All other responses - standard for Spring boot
