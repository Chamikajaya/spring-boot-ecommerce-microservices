## Spring Microservices Project for E-Commerce App

### Overview

* This project is a simple e-commerce application I built using Spring Boot and Spring Cloud, following the **microservices architecture**. It includes multiple services that handle various functionalities such as customer management, product management, order processing, payment handling, and notifications.

### Global Architecture

![global-architecture-draw-io.png.png](helper-diagrams%2Fglobal-architecture-draw-io.png.png)


### Tech stack I used

* Java - Main programming language

* Spring Boot - Framework for building microservices
* Spring Cloud - For microservices configuration and management

* Eureka - Service discovery
* Zipkin - Distributed tracing
* Docker - Containerization

* Flyway - Database migrations
* PostgreSQL - Database for Product, Order, and Payment services
* MongoDB - Database for Customer and Notification services
* Spring Data JPA - For database operations

* Feign Client - For inter-service HTTP communication
* Kafka - For asynchronous communication between microservices

**Further, the following design patterns were used for better maintainability**

* **n-tier architecture** -  Separating the application into layers such as presentation, business logic, and data access to enhance modularity and scalability.

* **producer-consumer pattern** - Using Kafka for asynchronous communication between microservices, where services act as producers and consumers of messages

* **DTO pattern** -  Implementing Data Transfer Objects (DTOs) to transfer data between different layers and services, ensuring loose coupling


### Main Architecture of the project

#### Main Components

* **Eureka Discovery Service**: Service discovery
* **Config Server**: Centralized configuration management
* **API Gateway**: Entry point for all client requests
* **Customer Service**: Manages customer-related operations
* **Product Service**: Manages product-related operations
* **Order Service**: Handles order-related operations
* **Payment Service**: Manages payment processing
* **Notification Service**: Sends email notifications for order and payment confirmations


#### Workflow of the application

1) **Order Placement:**
   * The Order Service validates the customer through the Customer Service.
   * It verifies product availability with the Product Service.
   * If successful, the order details are saved to the PostgreSQL database, and an order confirmation message is sent to Kafka.

2) **Payment Processing:**
   * The Order Service interacts with the Payment Service to process payments.
   * Upon successful payment, payment details are saved to the PostgreSQL database, and a payment confirmation message is sent to Kafka.

3) **Notification Handling:**
   * The Notification Service consumes messages from Kafka and saves notification details to MongoDB.

#### Security
* Clients interact with microservices through the API Gateway, which validates requests and forwards them to the appropriate services.

### ER Diagram

![er-diagram-draw-io.png.png](helper-diagrams%2Fer-diagram-draw-io.png.png)

### Asynchronous Communication with Kafka

**Producers:**
* Order Service sends messages to the "order-topic".
* Payment Service sends messages to the "payment-topic".

**Consumer:**
* Notification Service consumes messages from both "order-topic" and "payment-topic" and saves the details to MongoDB.

![kafka-async-communication-draw-io.png.png](helper-diagrams%2Fkafka-async-communication-draw-io.png.png)


### Further Enhancements I am planning to do

* Replace Eureka with Kubernetes for service discovery and load balancing.
* Implement a CI/CD pipeline with Jenkins and deploy to AWS EKS.

### Collaboration

* If you want to collaborate with me on this project, especially on creating a frontend for this project, please feel free to contact me. I am looking forward to working with you.

### Additional Screenshots

#### Eureka Server Dashboard

![eureka-dashboard.png](helper-diagrams%2Feureka-dashboard.png)

#### Customer POST request to API Gateway example

![customer-POST.png](helper-diagrams%2Fcustomer-POST.png)

#### Order POST request to API Gateway example

![order-POST.png](helper-diagrams%2Forder-POST.png)

#### Sample Order Confirmation email sent

![order-email.png](helper-diagrams%2Forder-email.png)

#### Sample Payment Confirmation email sent

![payment-email.png](helper-diagrams%2Fpayment-email.png)
