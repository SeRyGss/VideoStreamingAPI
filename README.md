# Video Management API Documentation

## Overview

This project is a Video Management API designed to allow users to upload, retrieve, and manage videos and their
associated metadata. The system supports features such as adding new videos, viewing existing ones, and performing soft
deletes (delisting) on videos.

## Base URL

```
http://localhost:8080/api/videos
```

---

## Endpoints

### 1. **POST /api/videos**

**Description:**  
This endpoint allows users to upload and publish a new video with its associated metadata.

**Request Body:**

```json
{
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere"
}
```

- `title` (string): Title of the video.
- `synopsis` (string): Brief description or synopsis of the video.
- `director` (string): The director of the video.
- `cast` (string): List of cast members.
- `yearOfRelease` (integer): The release year of the video.
- `genre` (string): Genre of the video.
- `runningTime` (integer): Running time in minutes.
- `content` (string): Base64 encoded video content.

**Response:**

- **200 OK:** If the video is successfully uploaded and published.

```json
{
  "id": 1,
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere",
  "isActive": true
}
```

- **400 Bad Request:** If the request body is invalid or any required fields are missing.

```json
{
  "error": "Video already exists"
}
```

### 2. **GET /api/videos/{id}**

**Description:**  
This endpoint allows users to retrieve information about a specific video by its ID.

**Path Parameters:**

- `id` (integer): The ID of the video.

**Response:**

- **200 OK:** If the video is found.

```json
{
  "id": 1,
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere",
  "isActive": true
}
```

- **400 Not Found:** If the video with the provided ID does not exist.

```json
{
  "error": "Video not found"
}
```

### 3. **PUT /api/videos/{id}**

**Description:**  
This endpoint allows users to update metadata of a video. It can modify fields like `title`, `synopsis`, `director`,
`cast`, etc.

**Path Parameters:**

- `id` (integer): The ID of the video to update.

**Request Body:**

```json
{
  "title": "Inception Updated",
  "synopsis": "A skilled thief is tasked with planting an idea into the mind of a target.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "UpdatedBase64EncodedContentHere"
}
```

**Response:**

- **200 OK:** If the video is successfully updated.

```json
{
  "id": 1,
  "title": "Inception Updated",
  "synopsis": "A skilled thief is tasked with planting an idea into the mind of a target.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "UpdatedBase64EncodedContentHere",
  "isActive": true
}
```

- **400 Bad Request:** If the request body is invalid.

```json
{
  "error": "Video not found"
}
```

### 4. **DELETE /api/videos/{id}**

**Description:**  
This endpoint allows users to perform a soft delete (delist) of a video. The video will no longer be visible to the
public but remains in the system for potential future restoration.

**Path Parameters:**

- `id` (integer): The ID of the video to soft delete.

**Response:**

- **200 OK:** If the video is successfully delisted.

```json
{
  "message": "Video delisted successfully"
}
```

- **400 Not Found:** If the video with the provided ID does not exist.

```json
{
  "error": "Video not found"
}
```

---

## Error Handling

The API provides detailed error messages to guide the client in resolving issues. Common errors include:

- **400 Bad Request**: Returned when the request body is invalid or a required parameter is missing or video is not
  found.
- **500 Internal Server Error**: If there is an unexpected server error.

---

## Example Requests

### 1. **Publish a Video**

**Request:**

```bash
POST /api/videos
Content-Type: application/json

{
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere"
}
```

**Response:**

```json
{
  "id": 1,
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere",
  "isActive": true
}
```

### 2. **Retrieve Video Information**

**Request:**

```bash
GET /api/videos/1
```

**Response:**

```json
{
  "id": 1,
  "title": "Inception",
  "synopsis": "A skilled thief is given a chance at redemption if he can successfully perform inception.",
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
  "yearOfRelease": 2010,
  "genre": "Sci-Fi",
  "runningTime": 148,
  "content": "Base64EncodedContentHere",
  "isActive": true
}
```

### 3. **Delist Video (Soft Delete)**

**Request:**

```bash
DELETE /api/videos/1
```

**Response:**

```json
{
  "message": "Video delisted successfully"
}
```

---

## Conclusion

This API allows for the complete management of video content, from publishing to managing metadata and performing soft
deletes (delisting) when necessary. The responses are designed to be clear and informative, making the system easy to
interact with for developers and users alike.

In resources there is attached the video streaming API postman collection.
