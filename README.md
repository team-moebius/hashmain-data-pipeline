# Moebius

## Definition
Moebius is a platform for supporting crypto currency trades conveniently, safely.
 
## Structure
1. Moebius-backend (api, domain, service ...)
2. Moebius-frontend (mvc)
3. Moebius-batch

## Technical specification
* Spring boot 2
* Spring webflux
* Spring security
* Spring data mongodb
* Mongodb reactive stream
* Mongodb atlas (Mongo 4.0.6)

## Features
* User management based on json web token & spring security
* Flexible Stoploss in trades


## Vm options
```
-Dspring.profiles.active=${profile}
-Dreactor.netty.http.server.accessLogEnabled=true
```