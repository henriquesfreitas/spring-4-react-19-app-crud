# Spring API Library - Backend

This project is the backend API for a simple library management system. It is built with Spring Boot and provides a RESTful interface for managing books.

## Technology Stack

*   **Java:** 21
*   **Spring Boot:** 4.0.4
*   **Spring Security:** For authentication and authorization with JWT.
*   **Spring Data JPA:** For database interaction.
*   **Spring Web:** For creating RESTful endpoints.
*   **Spring AMQP:** For asynchronous messaging with RabbitMQ.
*   **PostgreSQL:** The relational database for data persistence.
*   **RabbitMQ:** Message broker for handling asynchronous tasks (like buying a book).
*   **MapStruct:** 1.5.5.Final - For efficient and clean mapping between DTOs and Entities.
*   **Gradle:** The build automation tool.

## Prerequisites

Before you can run this project, you will need to have the following installed:

*   **JDK 21** (or newer)
*   **Gradle** (usually handled by the IntelliJ wrapper)
*   **PostgreSQL:** A running instance of PostgreSQL.
*   **RabbitMQ:** A running instance of RabbitMQ. A simple way to run it locally is with Docker:
    ```sh
    docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
    ```

## Local Development Setup

This project uses environment variables to handle sensitive configuration like database credentials and JWT secrets.

### 1. Create the `.env` file

Create a file named `.env` in the root of the `spring-api-library` project. Add the following content, replacing the values with your actual credentials:

```dotenv
# Environment variables for local development
# This file is listed in .gitignore and should NOT be committed.

DB_URL=jdbc:postgresql://your-database-host/neondb?sslmode=require
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_super_secret_and_long_jwt_signing_key
```

### 2. Configure IntelliJ IDEA

IntelliJ IDEA can be configured to automatically load the variables from your `.env` file when you run the application.

1.  Go to **Run -> Edit Configurations...**.
2.  Select your `SpringApiLibraryApplication` configuration.
3.  In the "Environment variables" field, click the icon on the right to open the editor.
4.  Click the **`+`** icon and select **"Add from file..."**.
5.  Choose the `.env` file you just created.
6.  Click **"Apply"** and then **"OK"**.

Now, when you run the application from IntelliJ, all the variables from the `.env` file will be available to Spring Boot.

## How to Run the Project

After completing the local development setup:

1.  Open the `spring-api-library` project in IntelliJ IDEA.
2.  Let Gradle synchronize and download all the dependencies.
3.  Run the `SpringApiLibraryApplication` class.

## Security

This application is secured using Spring Security and JSON Web Tokens (JWT).

### Default User

On the first run with an empty database, a default user will be created:

*   **Username:** `admin`
*   **Password:** `password`
