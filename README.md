# Bolt Assessment - D&D 5e API Integration

This is a Spring Boot application that integrates with the D&D 5e API to provide summary information about various game resources.

## Project Overview

The application provides a REST API endpoint that fetches and summarizes data from the D&D 5e API, including:

- Total number of classes
- Total number of spells
- Total number of monsters
- Total number of features

## Technologies Used

- Java 11
- Spring Boot 2.7.14
- Maven
- Lombok
- Spring Web

## API Endpoints

### GET /api/summary

Returns a summary of D&D 5e resources with the following information:

```json
{
  "totalClasses": number,
  "totalSpells": number,
  "totalMonsters": number,
  "totalFeatures": number
}
```

## Getting Started

### Prerequisites

- Java 11
- Maven

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on the default port 8080.

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
    ├── DndApiResponse.java
    └── DndResource.java
```
