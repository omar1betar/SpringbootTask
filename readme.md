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
# Example API

Below are example API requests for the operations defined in your `OrderController` class. These requests will help in creating, retrieving, updating, deleting orders, and processing payments.

### 1. **Create Order (POST)**: Create a new order

**Request**: `POST /orders`

**Request Body (JSON)**:

```json
{
  "product_name": "Smartphone",
  "description": "Electronics order for a smartphone",
  "amount": 599.99,
  "status": "NEW"
}
```

**Example cURL**:

```bash
curl -X POST http://localhost:8000/orders \
     -H "Content-Type: application/json" \
     -d '{
           "product_name": "Dining Table",
           "description": "Furniture order for a dining table",
           "amount": 499.99,
           "status": "NEW"
         }'
```

**Response (JSON)**:

```json
{
   "databaseId": 8,
   "orderId": "3f918a59-b405-11ef-b4cc-0242ac1b0002",
   "description": "Furniture order for a dining table",
   "amount": 499.99,
   "status": "NEW",
   "productName": "Dining Table"
}
```

---

### 2. **Get Order by ID (GET)**: Retrieve an order by ID

**Request**: `GET /orders/{id}`

**Example cURL**:

```bash
curl -X GET http://localhost:8000/orders/1
```

**Response (JSON)**:

```json

{
"databaseId": 3,
"orderId": "3f9188ea-b405-11ef-b4cc-0242ac1b0002",
"description": "Book order for a novel",
"amount": 19.99,
"status": "NEW",
"productName": "Novel"
}

```

---

### 3. **Get All Orders (GET)**: Retrieve a list of all orders

**Request**: `GET /orders`

**Example cURL**:

```bash
curl -X GET http://localhost:8000/orders
```

**Response (JSON)**:

```json
[
   {
      "databaseId": 8,
      "orderId": "3f918a59-b405-11ef-b4cc-0242ac1b0002",
      "description": "Furniture order for a dining table",
      "amount": 499.99,
      "status": "NEW",
      "productName": "Dining Table"
   },
   {
      "databaseId": 3,
      "orderId": "3f9188ea-b405-11ef-b4cc-0242ac1b0002",
      "description": "Book order for a novel",
      "amount": 19.99,
      "status": "NEW",
      "productName": "Novel"
   }
]
```

---

### 4. **Update Order (PUT)**: Update an existing order by ID

**Request**: `PUT /orders/{id}`

**Request Body (JSON)**:

```json
{
   "databaseId": 3,
   "orderId": "3f9188ea-b405-11ef-b4cc-0242ac1b0002",
   "description": "Book order for a novel",
   "amount": 19.99,
   "status": "NEW",
   "productName": "Novel"
}
```

**Example cURL**:

```bash
curl -X PUT http://localhost:8000/orders/1 \
     -H "Content-Type: application/json" \
     -d '{
  "description": "Purchase of electronics",
  "amount": 320.75,
  "status": "NEW",
  "productName":"tttt"
}'
```

**Response (JSON)**:

```json
{
   "databaseId": 1,
   "orderId": "dfa397d2-b404-11ef-b4cc-0242ac1b0002",
   "description": "Purchase of electronics",
   "amount": 320.75,
   "status": "NEW",
   "productName": "tttt"
}
```

---

### 5. **Delete Order (DELETE)**: Delete an order by ID

**Request**: `DELETE /orders/{id}`

**Example cURL**:

```bash
curl -X DELETE http://localhost:8000/orders/1
```

**Response (No Content)**:

```json
{}
```

---

### 6. **Process Payment (POST)**: Process payment for an order

**Request**: `POST /orders/{id}/payment`

**Request Parameters**: `amount` (the amount being paid)

**Example cURL**:

```bash
curl -X POST "http://localhost:8000/orders/1/payment?amount=599.99"
```

**Response (JSON)**:

1. **Payment Successful**:

```json
{
  "message": "Payment successful and order status updated to PAID."
}
```

2. **Payment Failed**:

```json
{
  "message": "Payment failed with message Insufficient funds"
}
```

---

### Explanation of the API Requests:

1. **Create Order (`POST /orders`)**: This request creates a new order in the system using the provided details such as product name, description, amount, and status. The response will include the details of the created order, including a generated `orderId`.

2. **Get Order by ID (`GET /orders/{id}`)**: This request retrieves the details of a specific order based on its unique `id`.

3. **Get All Orders (`GET /orders`)**: This request fetches all orders stored in the system. If no orders are found, a 204 No Content response is returned.

4. **Update Order (`PUT /orders/{id}`)**: This request allows updating the details of an existing order by `id`. The request body should contain the updated details, and the response will return the updated order.

5. **Delete Order (`DELETE /orders/{id}`)**: This request deletes an order based on its `id`. It returns a `204 No Content` response indicating that the order has been successfully deleted.

6. **Process Payment (`POST /orders/{id}/payment`)**: This request processes a payment for a specified order. It uses the `amount` parameter to specify the payment amount. The response indicates whether the payment was successful or failed and provides a message accordingly.

---

### Swagger Documentation:

Your controller uses **Swagger annotations** (`@Operation`, `@ApiResponse`, `@Tag`) to document each endpoint. If Swagger UI is enabled in your application, you can navigate to `/swagger-ui` or `/v3/api-docs` to view and test these API requests interactively.

---

## Notes
- Ensure RabbitMQ is running before starting the application.
- Cache expiration policies can be configured in Hazelcast settings.

Feel free to reach out with questions or issues!