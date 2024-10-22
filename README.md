# NBA Stats API

**RESTful API** developed in **Java Spring Boot** to provide statistics data for NBA players and teams.

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Docker
- Maven

## Key Features

* **Endpoints:**
  * **Players:**
    * `GET /nbaApp/api/v1/player`: Get a list of all players
    * `GET /nbaApp/api/v1/player/{id}`: Get detailed information about a player
    * `POST /nbaApp/api/v1/player`: Create a new player
    * `PATCH /nbaApp/api/v1/player/{id}`: Update an existing player
    * `DELETE /nbaApp/api/v1/player/{id}`: Delete a player
  * **Teams:**
    * `GET /nbaApp/api/v1/team`: Get a list of all teams
    * `GET /nbaApp/api/v1/team/{id}`: Get detailed information about a team
    * `POST /nbaApp/api/v1/team`: Create a new team
    * `PATCH /nbaApp/api/v1/team/{id}`: Update an existing team
    * `DELETE /nbaApp/api/v1/team/{id}`: Delete a team

* **Functionalities:**
  * Search players by name or team
  * Filter players by position, age, etc.
  * Create, update, and delete players and teams

## Running the Application

1. **Clone the Repository:**
   ```bash
   https://github.com/KevinLautaroSoto/NbaApp.git
   ```
2. **Configure the Database:**
   - Create a MySQL database (e.g., `nba_db`).
   - Apply any required migration scripts or use tools like **Flyway** or **Liquibase** to handle database migrations.
   - Update the `application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/nba_db
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```
3. **Build and Start the Application:**
   - **Maven**: 
     ```bash
     mvn clean install
     mvn spring-boot:run
     ```
   - **Docker** (optional): You can also run the app using Docker by building the image:
     ```bash
     docker-compose up --build
     ```

## Testing the API

Use **Postman** or **curl** to test the API endpoints. Example requests:

- **Get all players:**
  ```bash
  curl -X GET http://localhost:8080/nbaApp/api/v1/player
  ```
- **Get a specific player by ID:**
  ```bash
  curl -X GET http://localhost:8080/nbaApp/api/v1/player/{id}
  ```

## Future Enhancements

- Implement advanced search functionalities, filtering by position, team, and statistics.
- Provide the ability to create custom charts for player/team statistics.
