 # UserService Backend Application

A Spring Boot microservice for the Volna Food Delivery platform, designed to manage user profiles and delivery address operations. This service facilitates RESTful API communication, providing robust support for authentication, data persistence, and containerized development.
A Spring Boot microservice for the **Volna Food Delivery Backend**, responsible for authenticated user profile management and delivery address management.

The service provides REST APIs for user profiles and addresses, including address CRUD operations, default-address management, DTO validation, JWT-based authentication, PostgreSQL persistence, and Docker-supported development.

> Scope note: This repository currently focuses on the User_Service layer of the food delivery backend. User profile APIs and address APIs are implemented through Spring Boot controllers, services, repositories, DTOs, entities, Spring Security, and PostgreSQL. The APIs have been tested using Postman with successful `200 OK`, `201 Created`, and `204 No Content` responses.

## Stack

Backend: 
Java 
· Spring Boot 
· Spring MVC
· Spring Security
· JWT 
· Spring Data JPA 
· Hibernate 
· PostgreSQL 
· Maven
· Lombok 
· Jakarta Validation

Database: PostgreSQL

Authentication: Spring Security · JWT · @AuthenticationPrincipal

Testing: Postman

Containerization: Docker 
· Docker Desktop
· Docker Compose

 Folder structure
User-Service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── Volna/
│   │   │           └── User_Service/
│   │   │               ├── controller/
│   │   │               │   ├── UserController.java
│   │   │               │   └── AddressController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── AddressRequestDto.java
│   │   │               │   ├── AddressResponseDto.java
│   │   │               │   ├── UserProfileRequestDto.java
│   │   │               │   └── UserProfileResponseDto.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               ├── exception/
│   │   │               └── config/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── Dockerfile
└── README.md

Architecture
Client / Frontend / Postman
          ↓
     REST Controller
          ↓
    Request DTO + Validation
          ↓
       Service Layer
          ↓
      Repository Layer
          ↓
        JPA Entity
          ↓
       PostgreSQL
Authentication flow
Client
  ↓
JWT Bearer Token
  ↓
Spring Security
  ↓
Authenticated Principal
  ↓
@AuthenticationPrincipal String authUserId
  ↓
UUID userId
  ↓
UserService / AddressService
API Base URL

The UserService runs locally on:

http://localhost:8082

Base API path:

/api/v1/users
User Profile APIs
Get Current User Profile
GET /api/v1/users/me

Full URL

http://localhost:8082/api/v1/users/me

Authorization

Authorization: Bearer <JWT_TOKEN>

Response

{
  "avatarUrl": "https://example.com/avatar.jpg",
  "email": "testuser@example.com",
  "fullName": "Test User",
  "id": "f5445e16-7f3f-49c9-96ad-e78d8de00b9f",
  "phoneNumber": "+919876543211"
}

Test result: 200 OK ✅

Update Current User Profile
PUT /api/v1/users/me

Full URL

http://localhost:8082/api/v1/users/me

Headers

Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Example request

{
  "fullName": "Test User Updated",
  "phoneNumber": "+919876543211",
  "avatarUrl": "https://example.com/new-avatar.jpg"
}

Expected response: 200 OK

The endpoint is implemented in UserController using @PutMapping("/me"). Record the final Postman result here after the final verification.

Address APIs

Base address path:

/api/v1/users/addresses
Create Address
POST /api/v1/users/addresses

Example request

{
  "addressType": "HOME",
  "city": "Rewari",
  "houseNumber": "123",
  "isDefault": false,
  "landmark": "Near Bus Stand",
  "latitude": 28.199,
  "longitude": 76.617,
  "pincode": "123401",
  "state": "Haryana",
  "streetAddress": "Model Town"
}

Test result: 201 Created ✅

Get All Addresses
GET /api/v1/users/addresses

Test result: 200 OK ✅

Example response:

[
  {
    "addressType": "HOME",
    "city": "Rewari",
    "houseNumber": "123",
    "id": "address-uuid",
    "isDefault": true,
    "landmark": "Near Bus Stand",
    "latitude": 28.199,
    "longitude": 76.617,
    "pincode": "123401",
    "state": "Haryana",
    "streetAddress": "Model Town"
  }
]

An empty response is also valid:

[]

with 200 OK, meaning the authenticated user currently has no addresses.

Get Address by ID
GET /api/v1/users/addresses/{addressId}

Example

GET http://localhost:8082/api/v1/users/addresses/{addressId}

Test result: 200 OK ✅

Update Address
PUT /api/v1/users/addresses/{addressId}

Example request

{
  "addressType": "HOME",
  "city": "Rewari",
  "houseNumber": "789",
  "isDefault": true,
  "landmark": "Near City Mall",
  "latitude": 28.199,
  "longitude": 76.617,
  "pincode": "123401",
  "state": "Haryana",
  "streetAddress": "Model Town Updated Street"
}

Test result: 200 OK ✅

Validation note: isDefault is required by AddressRequestDto.

If it is omitted, the API returns:

{
  "status": 400,
  "message": "Input validation failed",
  "validationErrors": {
    "isDefault": "Default status must be specified"
  }
}
Make Address Default
PATCH /api/v1/users/addresses/{addressId}/default

Request body: None

Authorization

Authorization: Bearer <JWT_TOKEN>

Test result: 200 OK ✅

Example response:

{
  "addressType": "HOME",
  "city": "Rewari",
  "houseNumber": "123",
  "id": "ce7005d6-8098-4852-b17d-0d0cc7e2a4a7",
  "isDefault": true,
  "landmark": "Near Bus Stand",
  "latitude": 28.199,
  "longitude": 76.617,
  "pincode": "123401",
  "state": "Haryana",
  "streetAddress": "Model Town"
}
Delete Address
DELETE /api/v1/users/addresses/{addressId}

Test result: 204 No Content ✅

A successful 204 response does not contain a response body.

API Testing Summary
#	Method	Endpoint	Purpose	Status
1	POST	/api/v1/users/addresses	Create address	✅ 201 Created
2	GET	/api/v1/users/addresses	Get all addresses	✅ 200 OK
3	GET	/api/v1/users/addresses/{id}	Get address by ID	✅ 200 OK
4	PUT	/api/v1/users/addresses/{id}	Update address	✅ 200 OK
5	PATCH	/api/v1/users/addresses/{id}/default	Make default address	✅ 200 OK
6	DELETE	/api/v1/users/addresses/{id}	Delete address	✅ 204 No Content
7	GET	/api/v1/users/me	Get current profile	✅ 200 OK
8	PUT	/api/v1/users/me	Update current profile	🔄 Final verification
DTOs
AddressRequestDto

Used for:

Create Address
Update Address

Typical fields include:

addressType
city
houseNumber
isDefault
landmark
latitude
longitude
pincode
state
streetAddress
AddressResponseDto

Used to return address information to the client.

Example:

{
  "addressType": "HOME",
  "city": "Rewari",
  "houseNumber": "123",
  "id": "address-uuid",
  "isDefault": true,
  "landmark": "Near Bus Stand",
  "latitude": 28.199,
  "longitude": 76.617,
  "pincode": "123401",
  "state": "Haryana",
  "streetAddress": "Model Town"
}
UserProfileRequestDto

Used when updating the authenticated user's profile.

UserProfileResponseDto

Used when returning the authenticated user's profile.

Security

The UserService uses JWT authentication with Spring Security.

Protected APIs use:

Authorization: Bearer <JWT_TOKEN>

The controllers obtain the authenticated user through:

@AuthenticationPrincipal String authUserId

The ID is converted to a UUID before calling the service layer:

UUID userId = UUID.fromString(authUserId);

This prevents the client from selecting an arbitrary user ID through the request body for the profile/address operations shown above.

Security note: Never commit JWT tokens, passwords, database credentials, .env files, or other secrets to GitHub.

Error Handling
400 Bad Request

Returned when request validation fails.

Example:

{
  "status": 400,
  "message": "Input validation failed",
  "validationErrors": {
    "isDefault": "Default status must be specified"
  }
}
403 Forbidden

A 403 Forbidden response was encountered during early testing of the Update Address API. After correcting the security/authentication configuration, the request successfully reached application-level validation and the API could be tested normally.

404 Not Found

A 404 was observed when attempting to use an address ID after the address had already been deleted.

Example:

Address not found with ID: <addressId>

This is expected behavior when the requested resource no longer exists.

204 No Content

The Delete Address API returns:

204 No Content

when deletion is successful.

Database

The UserService uses PostgreSQL for persistent storage.

Recommended verification:

SELECT * FROM address;
SELECT * FROM user_profile;

Database verification should confirm:

Address creation
Address updates
Address deletion
isDefault status
User profile data

Update SQL table names if your JPA entity mappings use different table names.

Docker

Docker Desktop is used for the local development environment.

Example verification commands:

docker ps
docker compose ps

The current development environment includes a PostgreSQL container for UserService. Supporting services such as RabbitMQ and Keycloak may also be configured depending on the complete backend environment.

Example local ports
UserService  → 8082
PostgreSQL   → 5432
Keycloak     → configured Docker port
RabbitMQ     → configured Docker port

Always use the ports defined in the project's Docker Compose/configuration files as the source of truth.

Postman Testing Flow

Recommended testing sequence:

JWT Authentication
       ↓
Create Address
       ↓
Get All Addresses
       ↓
Get Address by ID
       ↓
Update Address
       ↓
Make Address Default
       ↓
Verify Default Address
       ↓
Delete Address
       ↓
Get Current User Profile
       ↓
Update Current User Profile
Environment Variables

Do not commit real credentials.

Example database configuration:

spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>
spring.datasource.username=<username>
spring.datasource.password=<password>

Recommended environment-specific configuration:

application.properties
application-local.properties
.env

Keep secrets outside the committed repository.

Getting Started
1. Clone the repository
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd User-Service
2. Configure PostgreSQL

Make sure PostgreSQL is running locally or through Docker.

Configure the required datasource properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>
spring.datasource.username=<username>
spring.datasource.password=<password>
3. Install dependencies

Using Maven:

mvn clean install
4. Run the application
mvn spring-boot:run

The UserService runs on:

http://localhost:8082
5. Test the APIs

Use Postman with the JWT Bearer token and the endpoints documented above.

Docker Setup

If Docker Compose is configured in the project:

docker compose up -d

Check running services:

docker compose ps

Stop services:

docker compose down
Project Status
Feature	Status
User profile retrieval	✅ Implemented
User profile update	🔄 Final API verification
Address creation	✅ Implemented & tested
Address retrieval	✅ Implemented & tested
Address retrieval by ID	✅ Implemented & tested
Address update	✅ Implemented & tested
Make default address	✅ Implemented & tested
Address deletion	✅ Implemented & tested
JWT authentication integration	✅ Implemented
DTO validation	✅ Implemented
PostgreSQL integration	✅ Implemented
Docker development environment	✅ Configured
Postman API testing	✅ Performed
Review Evidence

For technical review, the following screenshots are recommended:

Postman – Create Address
Postman – Get All Addresses
Postman – Get Address by ID
Postman – Update Address
Postman – Make Default Address
Postman – Delete Address
Postman – Get User Profile
Postman – Update User Profile
PostgreSQL – User/Profile records
PostgreSQL – Address records
Docker Desktop – Running containers

Mask JWT tokens, passwords, database credentials, and other sensitive information before committing screenshots.

Development Notes

The UserService follows a layered architecture:

Controller
   ↓
DTO + Validation
   ↓
Service
   ↓
Repository
   ↓
Entity
   ↓
PostgreSQL

Authentication is handled separately through Spring Security:

JWT
 ↓
Security Filter
 ↓
Authenticated Principal
 ↓
@AuthenticationPrincipal
 ↓
User UUID
 ↓
Service Layer

This keeps the controller layer focused on HTTP handling while business logic remains in the service layer.

License

This project is intended for development, learning, and project-review purposes.









