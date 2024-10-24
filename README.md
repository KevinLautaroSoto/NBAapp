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
    * `POST /nbaApp/api/v1/player`: Crea un nuevo jugador.
    * `GET /nbaApp/api/v1/player`: Obtiene una lista paginada de todos los jugadores (**soporta filtrado por nombre**).
    * `GET /nbaApp/api/v1/player/{id}`: Obtiene información detallada sobre un jugador.
    * `PUT /nbaApp/api/v1/player/{id}`: Actualiza un jugador existente.
    * `PATCH /nbaApp/api/v1/player/{id}`: Actualiza parcialmente un jugador existente.
    * `DELETE /nbaApp/api/v1/player/{id}`: Elimina un jugador.
    * `GET /nbaApp/api/v1/player/name/{name}`: Busca jugadores por parte de su nombre (sin distinción de mayúsculas y minúsculas).
  * **Teams:**
    * `POST /nbaApp/api/v1/team`: Crea un nuevo equipo.
    * `GET /nbaApp/api/v1/team`: Obtiene una lista paginada de todos los equipos (**soporta filtrado por nombre**).
    * `GET /nbaApp/api/v1/team/{id}`: Obtiene información detallada sobre un equipo.
    * `PUT /nbaApp/api/v1/team/{update/{id}}`: Actualiza un equipo existente.
    * `PATCH /nbaApp/api/v1/team/patch/{id}`: Actualiza parcialmente un equipo existente.
    * `DELETE /nbaApp/api/v1/team/{id}`: Elimina un equipo.
    * `GET /nbaApp/api/v1/team/name/{name}`: Busca equipos por parte de su nombre (sin distinción de mayúsculas y minúsculas).

* **Functionalities:**
  * Ability to create, update, and delete players and teams.
  * Pagination for retrieving large datasets efficiently (both players and teams).
  * Basic search functionalities by name (players and teams).

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
