# Employee Management System

This Spring Boot application provides REST APIs to manage employees and departments within an organization. It supports all core CRUD operations with proper validation, pagination, and error handling.

**Features**
 - Employee Management: Create, update, and view employee details

 - Department Management: Create, update, delete and view departments

**Advanced Operations**

 - Expand department view to include employees

 - Employee lookup (ID and name only)

 - Pagination: All GET endpoints support pagination

 - Error Handling: Comprehensive error responses with proper HTTP codes

**Technologies**

 - Java 21
 - Spring Boot 3
 - Spring Data JPA
 - MySQL Database

**API Endpoints**

**Employee Endpoints**

| Method   | Endpoint                     | Description                            |
|----------|------------------------------|----------------------------------------|
| POST     | /api/v1/employees            | Create new employee                    |
| PATCH    | /api/v1/employees/{id}       | Update employee details                |
| GET      | /api/v1/employees            | Get all employees (paginated)          |
| GET      | /api/v1/employees?lookup=true| Get employee names and IDs (paginated) |

**Department Endpoints**

| Method   | Endpoint                                 | Description                            |
|----------|------------------------------------------|----------------------------------------|
| POST     | /api/v1/departments                      | Create new department                  |
| PATCH    | /api/v1/departments/{id}                 | Update department details              |
| DELETE   | /api/v1/departments/{id}                 | Delete department                      |
| GET      | /api/v1/departments                      | Get all departments (paginated)        |
| GET      | /api/v1/departments/{id}?expand=employee | Get department with list of employees  |


**Department Table**

<img width="887" height="167" alt="image" src="https://github.com/user-attachments/assets/d7966eba-231b-4295-adb8-632b21df5afe" />

**Employee Table**
<img width="912" height="342" alt="image" src="https://github.com/user-attachments/assets/2b51532b-e2d4-414b-9c6d-4a90db7146a0" />


