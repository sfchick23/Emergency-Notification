# Emergency Notification System

## Project Description

This project is a cluster of 4 microservices developed using Spring Boot 3, Apache Kafka, Hibernate, and PostgreSQL. The main goal of the system is to send emergency notifications to users via SMS and email. The project ensures 100% message delivery by utilizing idempotency in Apache Kafka.

## Architecture

The project consists of the following microservices:






1. **Notification Service** - The main service that handles requests for sending notifications.
2. **SMS Service** - A service responsible for sending SMS messages through a third-party API.
3. **Email Service** - A service responsible for sending emails through a third-party API.
4. **User  Service** - A service that manages user data and their contact information.

## Technologies
- **Spirng Framework** -  Framework for building microservices.
- **Spring Boot 3** - Spring Boot is a framework based on the Spring Framework for simple and hassle—free development
-  **Spring Cache** - Built-in caching for improving application performance.
- **Apache Kafka** - Messaging system used to ensure reliable delivery of notifications.
- **Hibernate** - ORM for working with the PostgreSQL database.
- **PostgreSQL** - Relational database for storing user data and notifications.

## Step to run

### Prerequisites

- Java 17+
- Maven
- PostgreSQL
- Apache Kafka
- Docker

### Installation Steps

  - Clone the repository:

     ```bash
     git clone https://github.com/sfchick23/Emergency-Notification.git
     cd ShortCutUrl

  - Database Configuration

     - Change the portNo and dbName
     - Change the username and password
   
    ```properties
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.datasource.url=jdbc:postgresql://localhost:portNo/dbname
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    ```
    - Open the file
      - database-create.txt,
        create 3 tables according to the plan from the file

      - docker-compose.yaml,
        start the docker using this file
    
  - Run the application:
    ```bash
    mvn spring-boot:run
    ```
  - Access the Frontend:

    Open your browser and navigate to http://localhost:8080/notify/v2/person or http://localhost:8080/notify/v1/person to start using the system.
     
## How It Works

The system operates in two versions: v1 and v2. Both versions provide the same functionality but differ in their implementation:

- v1: Uses direct communication between services via REST API, specifically leveraging RestTemplate for HTTP requests. However, this approach does not guarantee 100% message delivery due to potential network issues, service unavailability, or other transient failures.
- v2: Uses Apache Kafka for asynchronous message processing, ensuring idempotency and reliable message delivery. By sending messages to Kafka topics, the system guarantees that messages will be processed and delivered even in the event of failures, retries, or system restarts.

### Frontend Microservice Endpoints

The `frontend` microservice exposes the following endpoints for interacting with the system:

#### Version 1 (v1)

- **GET** `/notify/v1/person` - Displays a form for creating a new user.
- **POST** `/notify/v1/person` - Submits the form to create a new user.
- **GET** `/notify/v1/send-sms` - Displays a form for sending an SMS.
- **POST** `/notify/v1/sms` - Submits the form to send an SMS. Note: This version uses RestTemplate and does not guarantee 100% message delivery.
- **GET** `/notify/v1/send-email` - Displays a form for sending an email.
- **POST** `/notify/v1/email` - Submits the form to send an email. Note: This version uses RestTemplate and does not guarantee 100% message delivery.

#### Version 2 (v2)

- **GET** `/notify/v2/person` - Displays a form for creating a new user.
- **POST** `/notify/v2/person` - Submits the form to create a new user.
- **GET** `/notify/v2/send-sms` - Displays a form for sending an SMS.
- **POST** `/notify/v2/sms` - Submits the form to send an SMS. This version sends the message to a Kafka topic, ensuring 100% message delivery.
- **GET** `/notify/v2/send-email` - Displays a form for sending an email.
- **POST** `/notify/v2/email` - Submits the form to send an email. This version sends the message to a Kafka topic, ensuring 100% message delivery.

### Workflow

1. **User Creation**: A user is created via the `/notify/vX/person` endpoint. The user's contact information is stored in the database.
2. **SMS Notification**: An SMS can be sent to a user via the `/notify/vX/sms` endpoint. The request is processed by the SMS Service, which sends the message using a third-party API.
3. **Email Notification**: An email can be sent to a user via the `/notify/vX/email` endpoint. The request is processed by the Email Service, which sends the email using a third-party API.
4. **Message Delivery**: In version **v2**, Apache Kafka ensures that messages are delivered reliably and idempotently, even in the case of failures.
