
# Java Back-end
## Using Spring, JPA, e JWT

A RESTFul API developed in Java using Spring, JPA and JsonWebToken to security (JWT). This API was developed as rest-service to the [Fox Social Network](github.com/leonardormlins/fox) Single-Page-Application.

## API endpoints

### User Controller

#### Login User

  Returns json data about a single user.

#### URL

  /users/login

#### Method

  `POST`
  
#### Data Params  
  
  `{ "name": <USERNAME>, "password": <PASSWORD> }`

#### Success Response:

  Code: 200 <br />
  Content: `{ "name" : <USERNAME>, "admin" : <TRUEORFALSE>, "token": <TOKEN> }`
 
#### Error Response:

  Code: 403 FORBIDDEN <br />
    Content: `{  }`

#### Sample call using axios:

  ```javascript
    axios.post( baseURL + 'user/login',
    { "name": name, "password": password });
  ```
#### Register User

  Returns json data about a single user.

#### URL

  /users/register

#### Method

  `POST`
  
#### Data Params  
  
  `{ "name": <USERNAME>, "password": <PASSWORD> }`

#### Success Response:

  Code: 200 <br />
  Content: `{ "name" : <USERNAME>, "admin" : <TRUEORFALSE>, "token": <TOKEN> }`
 
#### Error Response:

  Code: 403 FORBIDDEN <br />
    Content: `{  }`

#### Sample call using axios:

  ```javascript
    axios.post( baseURL + 'user/register',
    { "name": name, "password": password });
  ```


