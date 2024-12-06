# Spring Boot Order Management Application

## Overview
This project implements a Spring Boot application that provides order management functionality with the following features:
1. **CRUD Operations for Orders**: Supports creating, reading, updating, and deleting orders.
2. **Caching with Hazelcast**: Integrates Hazelcast to cache order data for optimized performance.
3. **Message Queue with RabbitMQ**: Handles payment events using RabbitMQ.
4. **Transaction Table**: Maintains payment information in a database table.

---

## Features
1. **Order CRUD Operations**:
    - Supports Create, Read, Update, and Delete operations on orders.
    - Uses a relational database (e.g., PostgreSQL/MySQL) for persisting orders.

2. **Hazelcast Caching**:
    - All orders are cached for quick retrieval.
    - Cache is updated after every CRUD operation.

3. **RabbitMQ Integration**:
    - Sends a message to a RabbitMQ queue when a payment is made for an order.
    - Implements a listener to consume payment messages and store payment data in a transactions table.

4. **Transaction Table**:
    - Stores payment information received via RabbitMQ.

5. **Documentation and Bonus Features**:
    - Swagger for API documentation.
    - A Dockerfile for containerizing the application.

---

## Prerequisites
Ensure the following tools are installed:
- Java 21
- Maven
- Docker
- RabbitMQ (Dockerized)
- Relational Database MySQL (Dockerized)

---

## Setup and Run Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/omar1betar/SpringbootTask.git
   cd SpringbootTask
   ```

2. **Run mvn to create Jar file**:
   ```bash
   mvn clean install 
   ```
3. **Run Docker Application**:
    - No need to configure `application.properties` already uploaded for fast and smoothness review.
    - This command will run 3 docker instances as per as the docker-compose file:
      ```bash
      docker-compose up 
      ```
      


4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

6. **Access API Documentation**:
    - Open [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html) for Swagger UI.

---


---

## Notes
- Ensure RabbitMQ is running before starting the application.
- Cache expiration policies can be configured in Hazelcast settings.

Feel free to reach out with questions or issues!