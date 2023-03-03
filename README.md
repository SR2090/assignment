To execute:
./gradlew bootrun

Requires MongoDb running at defaul port 27017.
Runs at port 8081
It can be configured int the application.yml file

com.playapp.starter.dataloading This package contains code to see database with events and a user.

NewUserRegistrationController
/v1/new-user-registration/signin 

Steps for token based authentication using prexisting user in database.
1. Default user "abc" password "123"
2. Api endpoint 
  http://localhost:8081/v1/new-user-registration/signin
3. Body raw JSON 
{
    "username":"abc",
    "password":"123"
}
4. Result will be a bearer token.
5. You can use it to call the PlayyoController api endpoints. The authorization should have a Bearer token_value.

/v1/new-user-registration/signup
Used to signup with new user
role can have values specified in UserRole.java class under com.playapp.starter.data package.
Example body in JSON format.
{
    "username": "exampleusername",
    "email": "example@gmail.com",
    "password": "examplepassword",
    "role": "ROLE_USER" 
}

PlayyoController
/v1/ public endpoint can be accessed without bearer token.
/v1/events returns existing events in database sorted in descending order by createdAt.
/v1/event-details/{id} Returns details about an event 
/v1/joinEvent allows a user to join an event
