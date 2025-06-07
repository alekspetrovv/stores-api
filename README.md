# Jumbo Stores API Backend

This project provides the backend API for managing Jumbo store information, including user authentication and store
data, with an emphasis on easy setup using Docker.

## Getting Started

Follow these steps to quickly set up the project with Docker and test the API using Postman.

### Prerequisites

* **Docker Desktop:** Ensure Docker Desktop is installed and
  running. [https://docs.docker.com/get-started/get-docker](https://docs.docker.com/get-started/get-docker/)
* **Postman:** Testing tool to test the API
  endpoints. [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone git@github.com:alekspetrovv/stores-api.git
   cd stores-api
   ```

2. **Start Docker Containers:**
   From the project root, start the database and backend services:
   ```bash
   docker compose up -d
   # or
   docker-compose up -d
   ```

### Testing Endpoints with Postman

1. **Import Postman Files:**
   Navigate to the `postman-api-collection/` directory in your cloned project. From there, import both the **Postman
   Environment** file (e.g., `Development_Environment.postman_environment.json`) and the **Postman Collection** file (
   e.g., `Stores.postman_collection.json`) into Postman.

2. **Select Environment:**
   Select the imported `Development` environment from the dropdown menu in the top-right of Postman.

3. **Run API Requests:**
   To access protected API resources, you must first authenticate. Begin by registering a new user account, then perform
   a login to receive your JWT (JSON Web Token). This token will grant you access to secure endpoints.

## Core Features & Engineering Practices

Beyond the core functionality, this project incorporates several engineering best practices to ensure production
readiness and maintainability.

* **Initial Data Loading:** The `stores.json` file is automatically loaded with all stores into the database when the application starts for the very first time (it skips loading if data already exists).
* **Store Search Endpoint:** Used PostgreSQL with PostGIS for geospatial data to efficiently find a configurable
  number of closest stores (defaulting to 5) to any given location.
* **JWT Authentication:** Implemented JWT-based authentication for user login and registration.
* **CRUD Stores:** Full CRUD (Create, Read, Update, Delete) capabilities for store entities.
* **API Production Best Practices:** Used Data Transfer Objects (DTOs) and automated mapping for clean API contracts and
  robust
  validation of incoming data.
* **Global Generic Error Handling:** Used generic and global exception handling to provide consistent and user-friendly
  error responses across the API.
* **Improved Performance:** Added caching mechanisms for frequently accessed data to optimize response times.
* **Automated Testing:** Includes a set of unit tests to ensure code quality and functionality.
* **Easier Deployment:** Makes it easy to set up and local development through Docker Compose.