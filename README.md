# Hospital Management System Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Java](https://img.shields.io/badge/Java-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2.0-EB5424?style=for-the-badge&logo=auth0&logoColor=white)
![JPA](https://img.shields.io/badge/JPA%2FHibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![REST API](https://img.shields.io/badge/REST%20API-009688?style=for-the-badge&logo=fastapi&logoColor=white)

A comprehensive, secure hospital management system backend built with Spring Boot, featuring role-based access control, appointment scheduling, patient management, and OAuth2 authentication.

## 🏗️ Architecture Overview

This project implements a monolithic hospital management system with clear separation of concerns and secure authentication mechanisms.

![UML Diagram](https://github.com/ismail-gits/hospital-management-system-backend-spring-boot/blob/main/uml-diagram.png)

## 🎯 Key Features

### 1. **Multi-Role Authentication & Authorization**
- JWT-based authentication
- OAuth2 social login (Google, GitHub, etc.)
- Role-based access control (RBAC)
- Permission-based authorization
- Secure password encryption

### 2. **User Management**
- Three primary roles: Admin, Doctor, Patient
- User registration and login
- Profile management
- Role-specific dashboards

### 3. **Doctor Management**
- Doctor onboarding by admin
- Department assignment
- Specialization tracking
- Doctor profile management
- View assigned patients and appointments

### 4. **Patient Management**
- Patient registration
- Medical history tracking
- Insurance information management
- Blood group records
- Patient profile updates

### 5. **Appointment System**
- Book appointments with doctors
- Appointment status tracking (Scheduled, Completed, Cancelled)
- View appointment history
- Manage appointment schedules

### 6. **Department Management**
- Multiple hospital departments
- Department-wise doctor allocation
- Specialty categorization

### 7. **Insurance Integration**
- Patient insurance details
- Insurance provider management
- Policy number tracking

## 🛠️ Technology Stack

### Core Framework
- **Spring Boot** - Main application framework
- **Spring MVC** - Web framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework

### Security
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Stateless authentication
- **OAuth2** - Social login integration
- **BCrypt** - Password hashing

### Database
- **PostgreSQL** - Relational database
- **HikariCP** - Connection pooling

### Build & Dependencies
- **Maven** - Dependency management
- **Lombok** - Boilerplate code reduction

### API Documentation
- **Springdoc OpenAPI** - API documentation (optional)

## 📋 Prerequisites

- **JDK 17** or higher
- **Maven 3.8+**
- **PostgreSQL 12+**
- **Git**
- **Postman** (for API testing)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ismail-gits/hospital-management-system-backend-spring-boot.git
cd hospital-management-system-backend-spring-boot
```

### 2. Configure PostgreSQL Database

Create a database for the application:

```sql
CREATE DATABASE hospital_management_db;
```

### 3. Update Application Configuration

Update `src/main/resources/application.yml` or `application.properties` with your database credentials:

**application.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospital_management_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: your_jwt_secret_key
  expiration: 86400000

spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your_google_client_id
            client-secret: your_google_client_secret
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 6. Initialize Sample Data

The application includes `data.sql` for initial data setup. It will be automatically executed on startup.

## 📡 API Endpoints

### Authentication Endpoints

```http
POST   /api/auth/signup           # User registration
POST   /api/auth/login            # User login
POST   /api/auth/oauth2/success   # OAuth2 callback
```

### Admin Endpoints

```http
POST   /api/admin/onboard-doctor  # Onboard new doctor
GET    /api/admin/doctors         # Get all doctors
GET    /api/admin/patients        # Get all patients
DELETE /api/admin/doctor/{id}     # Remove doctor
```

### Doctor Endpoints

```http
GET    /api/doctor/profile                    # Get doctor profile
PUT    /api/doctor/profile                    # Update doctor profile
GET    /api/doctor/appointments               # Get doctor's appointments
GET    /api/doctor/patients                   # Get assigned patients
PUT    /api/doctor/appointment/{id}/status    # Update appointment status
```

### Patient Endpoints

```http
GET    /api/patient/profile                # Get patient profile
PUT    /api/patient/profile                # Update patient profile
POST   /api/patient/appointment            # Book appointment
GET    /api/patient/appointments           # Get patient's appointments
DELETE /api/patient/appointment/{id}       # Cancel appointment
GET    /api/patient/doctors                # Get available doctors
```

### Hospital Endpoints

```http
GET    /api/hospital/departments           # Get all departments
GET    /api/hospital/doctors               # Get all doctors
GET    /api/hospital/doctors/{id}          # Get doctor details
```

## 🗄️ Database Schema

### Core Entities

#### User
- Primary authentication entity
- Links to Doctor or Patient profiles
- Stores credentials and OAuth2 provider info

#### Doctor
- Medical professional details
- Department association
- Specialization and qualifications
- Consultation fees

#### Patient
- Patient demographic information
- Medical history
- Blood group
- Insurance details

#### Appointment
- Doctor-Patient relationship
- Appointment date and time
- Status tracking
- Consultation notes

#### Department
- Hospital departments
- Specialty categorization

#### Insurance
- Insurance provider details
- Policy information
- Patient linkage

## 🔐 Security Features

### Authentication Methods

1. **JWT Authentication**
   - Stateless authentication
   - Token-based session management
   - Secure token generation and validation

2. **OAuth2 Social Login**
   - Google authentication
   - GitHub authentication (configurable)
   - Automatic user profile creation

### Authorization

**Role Hierarchy:**
```
ADMIN
├── Manage doctors
├── Manage patients
├── View all appointments
└── System configuration

DOCTOR
├── View assigned patients
├── Manage appointments
├── Update medical records
└── View department information

PATIENT
├── Book appointments
├── View medical history
├── Update profile
└── Manage insurance information
```

### Security Configurations

- **CORS Configuration** - Cross-origin resource sharing
- **CSRF Protection** - For form-based authentication
- **Password Encoding** - BCrypt hashing
- **JWT Filter** - Token validation on each request
- **Role-based Method Security** - `@PreAuthorize` annotations

## 🔧 Configuration

### Application Properties

Key configurations in `application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: hospital-management-system
  
  datasource:
    url: jdbc:postgresql://localhost:5432/hospital_management_db
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true

jwt:
  secret: ${JWT_SECRET:your-secret-key-change-in-production}
  expiration: 86400000 # 24 hours

logging:
  level:
    com.project.hospitalManagementSystem: DEBUG
    org.springframework.security: DEBUG
```

### Environment Variables

For production deployment:

```bash
export DB_URL=jdbc:postgresql://your-db-host:5432/hospital_db
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your-super-secret-jwt-key
export OAUTH2_GOOGLE_CLIENT_ID=your-google-client-id
export OAUTH2_GOOGLE_CLIENT_SECRET=your-google-client-secret
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

### API Testing with Postman

1. Import the Postman collection (if available)
2. Set environment variables (base URL, tokens)
3. Test endpoints sequentially

**Sample Login Request:**
```json
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "doctor@hospital.com",
  "password": "password123"
}
```

**Sample Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "doctor@hospital.com",
  "roles": ["DOCTOR"]
}
```

## 📦 Project Structure

```
hospital-management-system-backend/
├── src/
│   ├── main/
│   │   ├── java/com/project/hospitalManagementSystem/
│   │   │   ├── config/              # Application configuration
│   │   │   ├── controller/          # REST controllers
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── DoctorController.java
│   │   │   │   ├── PatientController.java
│   │   │   │   └── HospitalController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # JPA entities
│   │   │   │   ├── type/           # Enums
│   │   │   │   ├── User.java
│   │   │   │   ├── Doctor.java
│   │   │   │   ├── Patient.java
│   │   │   │   ├── Appointment.java
│   │   │   │   ├── Department.java
│   │   │   │   └── Insurance.java
│   │   │   ├── repository/          # Data repositories
│   │   │   ├── service/             # Business logic
│   │   │   ├── security/            # Security configuration
│   │   │   │   ├── config/
│   │   │   │   ├── filter/
│   │   │   │   ├── service/
│   │   │   │   └── util/
│   │   │   └── error/               # Exception handling
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application.properties
│   │       └── data.sql             # Initial data
│   └── test/                        # Test classes
├── pom.xml
└── README.md
```

## 🎨 Design Patterns Used

- **MVC Pattern** - Separation of concerns
- **DTO Pattern** - Data transfer between layers
- **Repository Pattern** - Data access abstraction
- **Service Layer Pattern** - Business logic encapsulation
- **Dependency Injection** - Loose coupling
- **Builder Pattern** - Object creation
- **Strategy Pattern** - Authentication strategies

## 🚀 Deployment

### Local Deployment

```bash
# Package as JAR
mvn clean package

# Run JAR
java -jar target/hospital-management-system-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Future Enhancement)

```bash
# Build Docker image
docker build -t hospital-management-system:latest .

# Run container
docker run -p 8080:8080 hospital-management-system:latest
```

> **Note:** Dockerfile will be added in future releases.

## 🔄 API Response Format

### Success Response

```json
{
  "status": "success",
  "data": {
    "id": 1,
    "name": "Dr. John Doe",
    "specialization": "Cardiology"
  },
  "message": "Doctor retrieved successfully"
}
```

### Error Response

```json
{
  "status": "error",
  "timestamp": "2024-01-20T10:30:00",
  "message": "Doctor not found",
  "details": "No doctor found with ID: 999",
  "path": "/api/doctor/999"
}
```

## 🛡️ Best Practices Implemented

- **Input Validation** - DTO validation with annotations
- **Exception Handling** - Global exception handler
- **Logging** - Structured logging with SLF4J
- **Security** - JWT + OAuth2 implementation
- **Code Organization** - Layered architecture
- **Database Optimization** - Proper indexing and relationships
- **API Documentation** - Clear endpoint documentation

## 🔮 Future Enhancements

- [ ] Add OpenAPI/Swagger documentation
- [ ] Implement email notifications
- [ ] Add prescription management
- [ ] Integrate billing system
- [ ] Add medical report generation
- [ ] Implement real-time chat between doctor and patient
- [ ] Add appointment reminder notifications
- [ ] Implement patient medical records upload
- [ ] Add analytics dashboard
- [ ] Docker containerization
- [ ] CI/CD pipeline setup

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 👤 Author

**Ismail**
- GitHub: [@ismail-gits](https://github.com/ismail-gits)

## 🙏 Acknowledgments

- Spring Boot team for excellent documentation
- Spring Security for robust security framework
- PostgreSQL community
- All contributors and supporters

## 📞 Support

For support, email mohammedismail72425@gmail.com or open an issue in the GitHub repository.

---

⭐ **Star this repository if you find it helpful!**
