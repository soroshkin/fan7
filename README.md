# com.outfit7.fun7

Service is implemented using hexagonal architecture. This helps to increase maintainability of application. 
Features are located in different packages, but they can use api of other feature. 

Call  `./gradlew clean build test` to run tests. (Make sure correct Java version path is set )

Call  `./gradlew bootRun` to start application.

**Assumptions**:
1. MongoDB is installed on machine, which will run this application.
2. Authorization is implemented via in-memory users due to time constraints
3. No Docker integration was added
4. User entity has only userId and gameCount fields, this is enough for current functional requirements, other fields are received from request.
5. Code coverage might be less than required due to time constraints