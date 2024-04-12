# Communication Protocol

This document outlines the communication protocols used in this project.

## Frontend to Backend Communication

Describe how the frontend communicates with the backend. Include details about the APIs used, the data format (JSON, XML, etc.), and any relevant endpoints.

### API Endpoints

## Course API

- `GET /api/courses`: This endpoint returns all the courses in the database. It does not require any parameters and returns a collection of all the courses.
- `GET /api/courses/{id}`: This endpoint returns the course that has the matching id as the path variable. If the course does not exist, it will return a NOT FOUND response. The `id` is a unique identifier for each course.
- `GET /api/courses/{courseId}/{providerId}/{keywordId}`: This endpoint returns the course that has the matching `courseId`, `providerId`, and `keywordId`. If the course does not exist, it will return a NOT FOUND response. The `courseId`, `providerId`, and `keywordId` are unique identifiers.
- `DELETE /api/courses/{id}`: This endpoint deletes a course from the API. The course is chosen by the given `id` parameter. If the course is not found, it will return a NOT FOUND response. If the course is found and deleted, it will return an OK response.
- `POST /api/courses`: This endpoint posts a new course into the API. The course cannot be null and responds with status BAD REQUEST if it is. If it is not null, it responds with status CREATED.
- `POST /api/courses/{courseId}/providers`: This endpoint posts a new provider into the API for the specified course. The provider cannot be null and responds with status BAD REQUEST if it is. If it is not null, it responds with status CREATED.
- `DELETE /api/courses/{courseId}/providers/{providerId}/keywords/{keywordId}`: This endpoint deletes a provider from the API, the provider is chosen by the given `courseId`, `providerId`, and `keywordId` parameters. If the provider is not found, it will return a BAD REQUEST response. If the provider is found and deleted, it will return an OK response.

## User API

- `GET /api/users`: This endpoint returns all the users in the database. It does not require any parameters and returns a collection of all the users.
- `POST /api/users/register`: This endpoint registers a new user into the API. The user details cannot be null and responds with status BAD REQUEST if they are. If the details are not null, it responds with status OK. If the username or email already exists, it responds with a BAD REQUEST status and an error message.
- `POST /api/users/login`: This endpoint authenticates a user in the API. The user credentials cannot be null and responds with status UNAUTHORIZED if they are. If the credentials are not null and match a user in the database, it responds with status OK and a JWT token.
- `GET /api/users/{username}`: This endpoint returns the profile of the user that has the matching username as the path variable. If the user does not exist, it will return a NOT FOUND response. If the user is not the same as the session user, it will return a FORBIDDEN response. The `username` is a unique identifier for each user.
- `PUT /api/users/{username}`: This endpoint updates the profile of the user that has the matching username as the path variable. If the user does not exist, it will return a NOT FOUND response. If the user is not the same as the session user, it will return a FORBIDDEN response. If the profile data is not supplied, it will return a BAD REQUEST response. If the profile data is supplied, it will update the user's profile and return an OK response. The `username` is a unique identifier for each user.

## Currency API

- `GET /api/currency`: This endpoint returns all the currencies available in the database. It does not require any parameters and returns a collection of all the currencies.

## Backend to Database Communication

Describe how the backend communicates with the database. Include details about the database technology used, the query language, and any conventions followed in the project.

### Database Queries

- `SELECT * FROM table1`: Describe what this query does.
- `INSERT INTO table2`: Describe what this query does.

## Error Handling

Describe how errors are handled in the project. Include details about how errors are logged, how they are returned to the user, and any conventions followed for error messages.

## Security Protocols

Describe any security protocols used in the project. This could include details about authentication, encryption, and data protection measures.

## Future Improvements

List any planned improvements to the communication protocols.
```

Remember to replace the placeholders with the actual details of your project.