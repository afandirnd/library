# Identity Microservice

### API Gateway

Manage user roles and privileges, as well as authenticate / authorize API request.

Development environment setup:

- Create a new file under src/main/resources and name it: application-\<your username\>.properties
- Copy the content of application.properties files and fill it with appropriate values into your local properties
- Add the following to your application vm options: -Dspring.profiles.active=\<your username\>