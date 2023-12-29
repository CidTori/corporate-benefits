# Corporate Benefits

This backend is a Spring Boot API to manage corporate benefits.

## Usage

The endpoints are:

- POST http://localhost:8080/employees/{employeeId}/meals: to send a meal deposit to an employe as an authenticated company
- POST http://localhost:8080/employees/{employeeId}/gifts: to send a gift deposit to an employe as an authenticated company
- GET http://localhost:8080/employees/{employeeId}/balance: to get an employee’s balance

Authentication is based on JWT, the secret is set with property `jwt.secret`.

## Running locally

On Linux or MacOS
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

On Windows
```shell
.\mvnw.cmd spring-boot:run -D"spring-boot.run.profiles"=dev
```

Swagger-UI is accessible at http://localhost:8080/swagger-ui.html
H2 Console is accessible at http://localhost:8080/h2-console

The database is already populated with employee `1` and company `1234567890` (default subject on jwt.io).

The JWT secret is `your-256-bit-secretyour-256-bit-secret` (twice the default secret on jwt.io, to actually reach 256 bit).

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.
