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

curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/echo
curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/invert
curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/flatten
curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/sum
curl -F "file=@matrix.csv" http://localhost:7451/v1/matrix-app/multiply
