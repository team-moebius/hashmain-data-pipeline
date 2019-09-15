# Moebius

## Definition
Moebius is a platform for supporting crypto currency trades conveniently, safely.
 
## Structure
1. Moebius-backend (api, domain, service)
2. Moebius-frontend
3. Moebius-batch

## Technical specification (backend)
* Spring boot 2
* Spring webflux
* Spring security
* Spring data with mongodb
* Reactive stream based on [Project Reactor](https://projectreactor.io)
* Mongodb 4.0.12

## Features
* User management based on json web token & spring security
* Flexible Stoploss in trades

## Vm options (backend)
```
-Dspring.profiles.active=${profile}
-Dreactor.netty.http.server.accessLogEnabled=true
```