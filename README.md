# Bolt Assessment - D&D 5e API Integration

This is a Spring Boot application that integrates with the D&D 5e API to provide information about various game resources. The application implements caching to improve performance and reduce API calls.

## Project Overview

The application provides REST API endpoints that fetch data from the D&D 5e API, including:

- Summary of total resources (classes, spells, monsters, features)
- Detailed information about specific D&D classes

All API responses are cached to improve performance and reduce load on the D&D 5e API.

## Technologies Used

- Java 11
- Spring Boot 2.7.14
- Maven
- Lombok
- Spring Web
- Spring Cache
- JUnit 5
- Mockito

## Configuration

The application can be configured through `application.properties`:

```properties
# Application name
spring.application.name=assessment

# D&D API configuration
dnd.api.base-url=https://www.dnd5eapi.co/api/2014

# Cache configuration
spring.cache.type=simple
```

## Installation

### Prerequisites

- Java 11 JDK
- Maven 3.6 or higher
- Git

### Setup Steps

1. Clone the repository:

```bash
git clone <repository-url>
cd bolt-assessment
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on port 8080. You can verify it's running by visiting:

```
http://localhost:8080/api/summary
```

## API Endpoints

### GET /api/summary

Returns a cached summary of D&D 5e resources with the following information:

#### Example Request

```bash
curl -X GET http://localhost:8080/api/summary
```

#### Example Response

```json
{
  "totalClasses": number,
  "totalSpells": number,
  "totalMonsters": number,
  "totalFeatures": number
}
```

### GET /api/classes/{className}

Returns cached detailed information about a specific D&D class:

#### Example Request

```bash
curl -X GET http://localhost:8080/api/classes/barbarian
```

#### Example Response

```json
{
  "name": "string",
  "hit_die": number,
  "proficiency_choices": [
    {
      "choose": number,
      "type": "string",
      "from": {
        "option_set_type": "string",
        "options": [
          {
            "item": {
              "index": "string",
              "name": "string",
              "url": "string"
            }
          }
        ]
      }
    }
  ],
  "saving_throws": [
    {
      "index": "string",
      "name": "string",
      "url": "string"
    }
  ]
}
```

## Caching Strategy

The application implements the following caching strategies:

- `apiSummary`: Caches the summary of all resources
- `classDetails`: Caches individual class details using the class name as the key
- `apiCounts`: Caches the count of resources for each endpoint

The cache type is configured as `simple` in-memory cache.

## Error Handling

The application handles the following scenarios:

- 404 Not Found: When a requested D&D class does not exist
- 500 Internal Server Error: For other unexpected errors

## Testing

The application includes comprehensive unit tests using JUnit 5 and Mockito. The test suite covers:

### Controller Tests (`DndControllerTest`)

- `getSummary_ShouldReturnApiSummary`: Verifies the summary endpoint returns correct counts
- `getClassDetails_ShouldReturnClassDetails`: Verifies the class details endpoint returns correct class information

### Service Tests (`DndApiServiceTest`)

- `getApiSummary_ShouldReturnCorrectCounts`: Verifies the service correctly aggregates counts from all endpoints
- `getClassDetails_ShouldReturnClassDetails`: Verifies the service correctly fetches and returns class details
- `getClassDetails_WhenApiFails_ShouldReturnNull`: Verifies proper error handling for non-existent classes

### Running Tests

```bash
# Run all tests
mvn test

# Run tests with detailed output
mvn test -Dtest=*Test

# Run specific test class
mvn test -Dtest=DndControllerTest

# Run specific test method
mvn test -Dtest=DndControllerTest#getSummary_ShouldReturnApiSummary
```

## Development

### Building for Production

```bash
mvn clean package
```

The executable JAR will be created in the `target` directory.

### Running the Production JAR

```bash
java -jar target/assessment-0.0.1-SNAPSHOT.jar
```

## Project Structure

```
src/main/java/com/bolt/assessment/
├── AssessmentApplication.java
├── controller/
│   └── DndController.java
├── service/
│   └── DndApiService.java
└── model/
    ├── ApiSummary.java
    ├── ClassDetail.java
    ├── DndApiResponse.java
    ├── DndResource.java
    ├── From.java
    ├── Item.java
    ├── Option.java
    ├── Proficiency.java
    └── ProficiencyChoice.java

src/test/java/com/bolt/assessment/
├── controller/
│   └── DndControllerTest.java
├── service/
│   └── DndApiServiceTest.java
└── AssessmentApplicationTests.java
```
