# Punch The Clock

## Users API

### List all:
Request: `GET /users`

### Get user:
Request: `GET /users/{id}`

### Create user:

Request: `POST /users`

```
{
  "name": "José",
  "cpf": "12345678901",
  "email": "jose@punchtheclock.com"
}
```

### Update user:

Request: `PUT /users/{id}`

```
{
  "name": "José Updated",
  "cpf": "12345678901",
  "email": "jose@punchtheclock.com"
}
```

### Delete user:
Request: `DELETE /users/{id}`

### Get user time registries:
Request: `GET /users/{id}/time-registries`

### Create user time registries:
Request: `POST /users/{id}/time-registries`

Punch In:
```
{ "type": "in" }
```

Punch Out:
```
{ "type": "out" }
```
