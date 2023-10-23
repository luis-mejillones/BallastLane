# BallastLane
Ballast Lane Applications - Technical Exercise - Java

# Learning Management System
## BE App (beapp)
beapp is the api to manage everything related to register courses, students, students take courses and log hours.

## Available endpoints:
### Create user
```
POST localhost:8080/user
{
    "id": null,
    "name": "Admin Master",
    "email": "admin@mail.com",
    "role": "ADMINISTRATOR"
}
```

### Create student
```
POST localhost:8080/student
{
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2000-10-20",
    "address": "At home",
    "email": "john@mail.com",
    "phoneNumber": "12345678"
}
```

### Get a student
```
GET localhost:8080/student/{studentId}
```

### Create course
```
POST localhost:8080/course/{emailAdmin}
{
    "name": "Course 01",
    "startDate": "2023-01-02"
}
```

### Edit any course
```
PUT localhost:8080/course/{emailAdmin}
{
    "id": 10,
    "name": "Test Course 02",
    "startDate": "2023-02-02"
}

```

### Get all courses
```
GET localhost:8080/course
```

### Student take course/s
```
POST localhost:8080/student/{studentId}/take-courses
[
    {courseId}
]
```

### Student log hours
```
POST localhost:8080/student/{studentId}/course/{courseId}/log-hours
{
    "date": "2023-10-22",
    "taskCategory": "RESEARCHING",
    "taskDescription": "Test log hours",
    "timeSpent": "2023-10-22T00:30:00.000000"
}
```

### Student edit log hour
```
PUT localhost:8080/log-hour
{
    "id": 1,
    "date": "2023-10-20",
    "taskCategory": "PRACTICING",
    "taskDescription": "Test log hours modified",
    "timeSpent": "2023-10-22T00:30:00.000000"
}
```

### Student delete log hour
```
DELETE localhost:8080/student/{studentId}/course/{courseId}/log-hours/{logHourId}
```

## FE App (beapp)
feapp is the frontend app which should be displayed at any browser.

> Note: Due to your authoring skills you are not a FE developer, however this is a very basic feature that only retrieves the first student (studentId=1) who calls the API.

The FE app can be opened in a browser with this link address http://localhost:4200
