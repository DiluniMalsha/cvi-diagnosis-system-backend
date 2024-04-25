# Cortical Visual Impairment Scoring System

## Overview
This Spring Boot project implements a scoring system for evaluating characteristics associated with Cortical Visual Impairment (CVI). CVI is a neurological condition affecting visual processing in the brain. The scoring system helps assess various aspects of CVI in individuals, aiding in diagnosis and intervention planning.

## Features
- **Characteristic Evaluation**: The system allows for the evaluation of specific characteristics related to CVI, such as visual attention, color preference, and visual field preference.
- **Scoring Algorithm**: A scoring algorithm is implemented to assign scores to each characteristic based on input data and predefined criteria.
- **User Management**: User authentication and authorization functionalities are included to ensure secure access to the scoring system.
- **Data Storage**: Data on characteristic scores and evaluations are stored in a relational database for future reference and analysis.

## Technologies Used
- **Spring Boot**: Provides the foundation for building the application, including RESTful APIs, data access, and security.
- **Spring Security**: Implements authentication and authorization features to control access to the scoring system.
- **Spring Data JPA**: Simplifies data access by providing repositories and entities for interacting with the database.
- **MySQL Database**: Stores data related to characteristic scores and evaluations.

## Getting Started
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Maven for building the project
- MySQL database server

### Database Setup
1. Create a MySQL database named `cvi_scoring_system`.
2. Update the database configuration in `application.properties` with your MySQL credentials.

### Running the Application
1. Clone the repository: `git clone https://github.com/your/repository.git`
2. Navigate to the project directory: `cd cvi-detection`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar target/cvi-scoring-system.jar`

### Accessing the System
Once the application is running, access it via [http://localhost:8080](http://localhost:8080).

## API Endpoints
- **GET /characteristics**: Retrieves a list of characteristics for evaluation.
- **POST /evaluation**: Submits an evaluation for a characteristic and receives a score.
- **GET /evaluations**: Retrieves all previous evaluations with scores.

## Contribution Guidelines
Contributions to the project are welcome. Please follow these guidelines:
- Fork the repository and create a new branch for your feature or bug fix.
- Commit your changes with clear and descriptive messages.
- Submit a pull request detailing the changes made and their purpose.
