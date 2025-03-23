# eCommerce Microservices Project

## Introduction
This is a scalable and optimized eCommerce platform built using a microservices architecture. The project is designed to handle high traffic and efficiently manage different aspects of an eCommerce system, such as product listing, order processing, payment handling, and user authentication.

### **Technologies Used**
- **Spring Boot** - Microservices framework
- **Spring Cloud** - Service discovery and API Gateway
- **Eureka Server** - Service Registry for Microservices
- **Feign Client & Load Balancer** - Inter-service communication
- **RabbitMQ** - Asynchronous messaging system
- **Redis** - Caching mechanism for optimized performance
- **Kafka** - Event-driven architecture
- **API Gateway** - Unified entry point for external requests
- **MySQL/PostgreSQL** - Database management
- **Docker** - Containerization
- **Kubernetes (Optional)** - Deployment and scaling

---
## Installation & Setup

### **Prerequisites**
Before setting up the project, ensure you have the following installed:

- **JDK 17 or later**
- **Maven** (For building the project)
- **Docker** (For containerized deployment)
- **RabbitMQ** (For message brokering)
- **Redis** (For caching)
- **Kafka** (For event-driven communication)
- **MySQL/PostgreSQL** (Database)

### **Steps to Install and Run**

#### **1. Clone the Repository**
```bash
  git clone https://github.com/your-repository.git
  cd ecommerce-microservices
```

#### **2. Set Up Configuration**
Update the `application.yml` files in each microservice with your database credentials and configurations.

#### **3. Start Essential Services**
Ensure you have Docker installed, then run the following to start necessary services:
```bash
docker-compose up -d
```
This will start RabbitMQ, Redis, MySQL, and Kafka.

#### **4. Start the Eureka Server**
Navigate to the Eureka server directory and run:
```bash
mvn spring-boot:run
```

#### **5. Start Each Microservice**
Navigate to each microservice directory and run:
```bash
mvn spring-boot:run
```
Repeat for all microservices: **ProductService, OrderService, PaymentService, UserService, API Gateway, etc.**

#### **6. Access the Application**
Once all microservices are running, the application can be accessed via:
- **API Gateway**: `http://localhost:8080/`
- **Eureka Dashboard**: `http://localhost:8761/`
- **RabbitMQ Management Console**: `http://localhost:15672/`
- **Kafka UI (if installed)**: `http://localhost:9000/`


---
## Conclusion
This project provides a complete eCommerce system using microservices. It is designed for high scalability and performance, making it an ideal foundation for real-world enterprise applications.

**For any issues or contributions, feel free to submit a pull request or open an issue.** 🚀

