# Test task
It is necessary to write a service for “banking” operations. There are users (clients) in our system, each client has strictly one “bank account”, which initially contains some amount. Money can be transferred between clients. Interest is also accrued on the funds.

## Functional requirements:
There are users in the system, each user has strictly one “bank account". 
The user also has a phone number and email address. There must be at least one phone number and or email address. 
There must be some initial amount in the “bank account”. The user must also have a date of birth and full name.

For simplicity, we will assume that there are no roles in the system, only ordinary clients.
Let there be a service API (with open access) through which you can create new users in the system
by specifying a username, password, initial amount, phone and email (login, phone and email should not be occupied). 
The client's account balance cannot go into negative territory under any circumstances.
The user can add/change their phone number and/or email if they are not already occupied by other users.
The user can delete their phone and/or email. However, you cannot delete the last one.

The user cannot change the rest of the data.

Make a search API. You can search for any client. There should be filtering and pagination/sorting. Filters:
  If the date of birth is passed, then filter records where the date of birth is greater than the one passed in the request.
  If the phone number is transferred, then the filter is based on 100% similarity.
  If the full name is passed, then filter by like format ‘{text-from-request-param}%’
  If an email is sent, then the filter is based on 100% similarity. 

Access to the API must be authenticated (except for the service API for creating new clients).

Once a minute, the balance of each client is increased by 5%, but not more than 207% of the initial deposit.
For example:
It was: 100, it became: 105.
It was: 105, it became:110.25.
Implement the functionality of transferring money from one account to another. From an authenticated user's account to another user's account. Make all necessary validations and thread-safe.


## Non-functional Requirements:
Add OpenAPI/Swagger
Add Logging
Authentication via JWT.
We need to do tests to cover the functionality of money transfer.

### Stack:
Java 17
Spring Boot 3
PostgreSQL database
Maven
REST API
Additional technologies (Redis, ElasticSearch, etc.) are at your discretion.
You don't need a frontend

The result should be provided as a public repository on github.




## It was as difficult as possible to keep up with the week given for the task, due to lack of time, Logging, Swagger and tests were not implemented in the test task.
