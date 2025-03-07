# Tasks Manager

A simple RESTful API service for managing tasks.

## Setup Instructions

### Prerequisites

- Docker
- Docker Compose

### Running the Project Locally

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/tasks-manager.git
    cd tasks-manager
    ```

2. Build and run the application using Docker Compose:

    ```bash
    docker-compose up --build
    ```

3. The application will be available at `http://localhost:8080`.

### Accessing Swagger UI

Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## Deployment Details

The application is deployed at: https://tasks-manager-7tz3.onrender.com

## API Usage

### Endpoints

- **Create a new task**

    ```http
    POST /tasks
    ```

  **Request Body:**

    ```json
    {
        "title": "New Task",
        "description": "Task description",
        "status": "pending"
    }
    ```

  **Response:**

    ```json
    {
        "id": 1,
        "title": "New Task",
        "description": "Task description",
        "status": "pending",
        "created_at": "2023-10-01T12:00:00Z",
        "updated_at": "2023-10-01T12:00:00Z"
    }
    ```

- **Retrieve a list of all tasks**

    ```http
    GET /tasks
    ```

  **Response:**

    ```json
    [
        {
            "id": 1,
            "title": "New Task",
            "description": "Task description",
            "status": "pending",
            "created_at": "2023-10-01T12:00:00Z",
            "updated_at": "2023-10-01T12:00:00Z"
        }
    ]
    ```

- **Retrieve a single task by ID**

    ```http
    GET /tasks/{id}
    ```

  **Response:**

    ```json
    {
        "id": 1,
        "title": "New Task",
        "description": "Task description",
        "status": "pending",
        "created_at": "2023-10-01T12:00:00Z",
        "updated_at": "2023-10-01T12:00:00Z"
    }
    ```

- **Update an existing task by ID**

    ```http
    PUT /tasks/{id}
    ```

  **Request Body:**

    ```json
    {
        "title": "Updated Task",
        "description": "Updated description",
        "status": "completed"
    }
    ```

  **Response:**

    ```json
    {
        "id": 1,
        "title": "Updated Task",
        "description": "Updated description",
        "status": "completed",
        "created_at": "2023-10-01T12:00:00Z",
        "updated_at": "2023-10-01T12:30:00Z"
    }
    ```

- **Delete a task by ID**

    ```http
    DELETE /tasks/{id}
    ```

  **Response:**

    ```json
    {
        "message": "Task deleted successfully"
    }
    ```

## Postman Collection

You can import the following Postman collection to simplify testing:

/postman/tasks-manager.postman_collection.json

## Swagger / OpenAPI Documentation

Swagger UI is available at `http://localhost:8080/swagger-ui.html`.