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

## Installation and Setup

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Apache Kafka

### Installation Steps

1. Clone the repository:
