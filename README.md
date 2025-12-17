# Marketing Website Backend

Spring Boot backend service for the marketing website project.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 15+ (or use Docker Compose)

## Getting Started

### 1. Start PostgreSQL Database

Using Docker Compose (recommended):
```bash
cd ../
docker-compose up -d
```

Or use your local PostgreSQL installation and create a database named `marketing_db`.

### 2. Configure Database Connection

Edit `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/marketing_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## Default Credentials

- **Username:** admin
- **Email:** admin@example.com
- **Password:** admin123

## API Endpoints

### Public Endpoints (No Authentication Required)

- `GET /api/public/gallery` - Get all gallery items
- `GET /api/public/gallery/{id}` - Get gallery item by ID
- `GET /api/public/about` - Get about us information
- `GET /api/public/contact` - Get contact information

### Authentication Endpoints

- `POST /api/auth/register` - Register new admin
- `POST /api/auth/login` - Login

### Admin Endpoints (Requires JWT Token)

**Gallery Management:**
- `GET /api/admin/gallery` - Get all gallery items
- `GET /api/admin/gallery/{id}` - Get gallery item by ID
- `POST /api/admin/gallery` - Create new gallery item
- `PUT /api/admin/gallery/{id}` - Update gallery item
- `DELETE /api/admin/gallery/{id}` - Delete gallery item
- `POST /api/admin/gallery/reorder` - Reorder gallery items

**About Us Management:**
- `GET /api/admin/about` - Get about us information
- `PUT /api/admin/about` - Update about us information

**Contact Management:**
- `GET /api/admin/contact` - Get contact information
- `PUT /api/admin/contact` - Update contact information

**File Upload:**
- `POST /api/admin/upload` - Upload image file

## File Storage

Uploaded files are stored in the `uploads/` directory in the project root.
Files are accessible via `/uploads/{filename}` URL.

## Authentication

The API uses JWT (JSON Web Token) for authentication.

To access admin endpoints:
1. Login via `/api/auth/login`
2. Include the JWT token in the Authorization header:
   ```
   Authorization: Bearer {your-jwt-token}
   ```

## Project Structure

```
src/main/java/com/marketing/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── repository/      # JPA repositories
├── security/        # Security configuration
└── service/         # Business logic services
```

## Technologies Used

- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Lombok
- Maven
