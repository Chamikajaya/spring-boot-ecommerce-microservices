**Refer amigoscode & dockerrize the services & eureka server - then Kubernetes EKS**

**We could place the required files among multiple services in a common-shared folder. But I am assuming a real life scenario where different microservices is implemented using different tech stack. So some code duplication !**

### TODOs -->
* What is reference field in Order entity ?

* Understand the importance of Flyway
* Understand the relationship in OrderLine
**Add flyway to the order service**
**Change the ports**

**Once done with services - dockerize them and refactor to k8s instead of eureka**

*
* Add kafka related notes - covering main topics
* Understand the importance of flyway
* Check toProduct method in ProductMapper for building the category !
* Understand those links to services in all applications.yml files

* What does the config server do here ? - refer its application.yml

**CV - Project description**

-- followed ATOM principles
-- Good sql practices - create indexes etc... add more such + Flyway for db migrations
-- Kafka - for async communication
-- Eureka - for service discovery (Later after learning about k8s - refactored)
-- Design Patterns used -> n tier architecture, producer-consumer , DTO pattern (double check)