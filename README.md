# Library Management System

Follow the following steps to get this project up and running on your local environment. Note that you may need to install some software tools (ex. PostgreSQL)

### Identity Service

- Create a new database in your Postgres environment
- Create a new file under Identity/src/main/resources and name it: application-\<your username\>.yml
- Copy vacant properties from application.yml and paste it inside your yml file
- Fill in the corresponding information (database related like url, username and password)
- You may want to set the following property for database creation and update: ```spring.jpq.hibernate.ddl-auto=update```
- Insert a new user into user table (after being created by spring), fill in your email and a random uuid, set the role to ROLE_ADMIN and the following in the password field: ```$2a$10$e3Wb550XZ.G0B6Nd2oN8WOU8Sgk8xoNPmdX7ALjiSnraMbL9i8weS``` which translate into 1234 when you login.

### Book Service
Repeat same steps for identity service, No need for any database insertion.

### Notification Service

- Create a new file under notification/src/main/resources and name it: application-\<your username\>.yml
- Copy vacant properties from application.yml and paste it inside your yml file
- Fill in the corresponding information, those will be used to sent out notifications

## Run/Debug configurations

Create a run configuration for each service. For Book, Notification and Identity service, include the following vm option:
```-Dspring.profiles.active=\<your username\>```

## Running the application
Download and install Kafka, use the following link for instructions: ```https://kafka.apache.org/quickstart```, note that you need to start all services as stated in the instructions.

After all is set, start running microservices in the following order:
- eureka-server
- api-gateway
- rest of service