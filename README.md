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

## Development

### Running Tests

```bash
mvn test
```

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
```
