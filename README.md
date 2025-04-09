# 🧮 Matrix App

A simple Spring Boot application that accepts a CSV file containing a matrix and performs the following operations:

- Echo (return matrix as-is)
- Invert (transpose the matrix)
- Flatten (convert to 1D string)
- Sum (sum of all elements)
- Multiply (product of all elements)

---

## How to Run

Make sure you have [Java 17+](https://adoptopenjdk.net/) and [Maven](https://maven.apache.org/) installed.

Run the application with: mvn spring-boot:run

## APIs
All APIs are POST requests and accept a CSV file as input using a multipart/form-data request.

1. curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/echo
2. curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/invert
3. curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/flatten
4. curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/sum
5. curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/multiply
