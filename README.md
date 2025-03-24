# Finance Manager Playground

This is a playground project for an application using:

- **Spring Modulith**
- **Kafka Externalization**
- **Spring ApplicationEvents**

## How to Run

### 1. Start the Required Services
Run the MariaDB and Kafka Docker Compose scripts located under:

- `src/main/resources/environment/db`
- `src/main/resources/environment/kafka`

Execute the following command in each directory:
```sh
docker-compose up -d
```

### 2. Start the Finance Manager Application
Run the Spring Boot application to enable the available REST endpoints.

## Available REST Endpoints

### Create an Account
```sh
curl -X POST "http://localhost:8080/api/accounts" \
     -H "Content-Type: application/json" \
     -d '{
           "accountName": "Mein Sparkonto",
           "initialBalance": 1000.00
         }'
```

### Create & Send a Transaction
```sh
curl -X POST "http://localhost:8080/api/transactions" \
     -H "Content-Type: application/json" \
     -d '{
           "financialTransactionType": "DEPOSIT",
           "amount": 10.00,
           "currency": "EUR",
           "transactionDate": "2025-03-15T18:00:00",
           "categoryId": 5,
           "accountId": 1,
           "cleared": false
         }'
```

## Workflow Execution
Once a transaction is created, the workflow will start automatically.
The results will be stored in the database tables:

- **`account`**
- **`transaction`**
