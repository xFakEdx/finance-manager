Just a playground for an app using Spring Modulith, Kafka Externalization and Spring ApplicationEvents

HowTo

1. Run the mariaDB and kafka docker-compose scripts under path src/main/resources/environment/db and 
   src/main/resources/environment/kafka for instance with the command docker-compose up -d
2. Run the finance manager SpringBoot application.
3. There will be 2 REST Endpinots available to create an account and to send a transaction. The transaction should 
   start the workflow.
4. Creating an account with:

curl -X POST "http://localhost:8080/api/accounts" \
-H "Content-Type: application/json" \
-d '{
"accountName": "Mein Sparkonto",
"initialBalance": 1000.00
}'

5. Creating/sending a transaction with:

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

After this steps the workflow should start and the results will be stored in the database tables account and 
transaction.



