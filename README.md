# stockapp
 Stock Application
 stockapp is a springboot REST application and stockapp-impl is Kotlin implementation application.
 
**Steps & Commands**

- [x] pull mongo image from docker hub **`docker pull mongo:latest`**
- [x] run mongo image **`docker run -d -p 27017:27017 --name stockmongodb mongo:latest`**
- [x] dockerize spring boot application **`docker build -t stockapp-mongodb:1.0 .`**
- [x] run spring boot docker image and link that container to mongo container 
   **`docker run -p 8080:8080 --name stockapp-mongodb --link stockmongodb:mongo -d stockapp-mongodb:1.0`**
- [x] check docker running containers  **`docker ps`** it should display two container ids
- [x] check logs of spring boot image **`docker logs springboot-mongodb`**

- [x] login to mongo terminal to verify records **`docker exec -it stockmongodb bash`**
- type mongo and enter
- show dbs
- use Stock
- show collections
- db.stock.find().pretty()


**Use Docker Compose**

- [x] Kill running container:
```
docker rm <containerId>
```


#### docker-compose.yml

```yaml
version: "3"
services:
  stockmongodb:
    image: mongo:latest
    container_name: "stockmongodb"
    ports:
      - 27017:27017
  stockapp-mongodb:
    image: stockapp-mongodb:1.0
    container_name: stockapp-mongodb
    ports:
      - 8080:8080
    links:
      - stockmongodb
```
- [x] navigate to resources folder:
```
/src/main/resources and run  docker-compose up
```
 **Swagger UI**
 
 You can access swagger-ui and API information with link below
 http://localhost:8080/swagger-ui.html
