**Refer amigoscode & dockerrize the services & eureka server - then Kubernetes EKS**


### TODOs -->
**Create the detailed ReadMe file + Kafka related notes**



**Once done with services - dockerize them and refactor to k8s instead of eureka**

*
* Add kafka related notes - covering main topics


**CV - Project description**

-- followed ATOM principles
-- Good sql practices - create indexes etc... add more such + Flyway for db migrations
-- Kafka - for async communication
-- Eureka - for service discovery (Later after learning about k8s - refactored)
-- Design Patterns used -> n tier architecture, producer-consumer , DTO pattern (double check)
-- Kafka - for async communication
-- Zipkin - for distributed tracing - can use Zipkin to help you track requests as they traverse through these services. This can be useful for understanding the latency of these requests and the nature of the requests' paths.



services:




mongo:
container_name: mongo_microservices_project
image: mongo
volumes:
- mongodb_microservices_project:/data/db
ports:
- "27017:27017"
environment:
MONGO_INITDB_ROOT_USERNAME: chamika
MONGO_INITDB_ROOT_PASSWORD: password
networks:
- microservices_project_network
restart: unless-stopped


mongo-express:
container_name: mongo_express_microservices_project
image: mongo-express
ports:
- "8081:8081"
environment:
ME_CONFIG_MONGODB_SERVER: mongo
ME_CONFIG_MONGODB_ADMINUSERNAME: chamika
ME_CONFIG_MONGODB_ADMINPASSWORD: password
ME_CONFIG_MONGODB_PORT: 27017

    networks:
      - microservices_project_network
    restart: unless-stopped

maildev:
container_name: maildev_microservices_project
image: maildev/maildev
ports:
- "1080:1080"
- "1025:1025"
restart: unless-stopped
networks:
- microservices_project_network



zipkin:
image: openzipkin/zipkin
container_name: zipkin_microservices_project
ports:
- "9411:9411"
networks:
- microservices_project_network



config-server:
image: chamikajay/ms-config-server:0.0.4-SNAPSHOT  # TODO: change this to the latest version
container_name: config_server_microservices_project
ports:
- "8888:8888"
networks:
- microservices_project_network
restart: unless-stopped

eureka-server:
image: chamikajay/ms-eureka:latest  # TODO: change this to the latest version
container_name: eureka_server_microservices_project
depends_on:
- config-server
ports:
- "8761:8761"
networks:
- microservices_project_network
restart: unless-stopped

customer:
image: chamikajay/ms-customer:latest
container_name: customer_microservices_project
ports:
- "8090:8090"
depends_on:
- config-server
- eureka-server
- mongo
networks:
- microservices_project_network
restart: unless-stopped


apigw:
image: chamikajay/api-gateway:latest
container_name: apigw_microservices_project
ports:
- "8222:8222"
depends_on:
- config-server
- eureka-server
- customer


    networks:
      - microservices_project_network
    restart: unless-stopped


networks:
microservices_project_network:
driver: bridge

volumes:
postgres_microservices_project:
mongodb_microservices_project:




****