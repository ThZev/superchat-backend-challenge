#Backend-challenge project

## Run application locally
Build and deploy database:
```shell script
docker-compose -f src/main/docker/docker-compose-db.yml up -d
```

Run application in dev mode with hot code swap enabled:
```shell script
./gradlew quarkusDev
```
## Run application in docker 
Package application:
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```
Build and deploy application with docker:
```shell script
docker-compose up -d
```

## Postman
You can find a postman collection under /postman.
Make use of it to test the application. 
