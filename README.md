## Junior hometask

## Room with light

#### This is a WEB application that represents the room interaction interface


### Task Description: 
- The user can create a room from a list of countries and view a list of rooms created by other users.

- Country is required to restrict access to the room

- He can enter the room and change the state of the light bulb only when his country matches with the country of the room.

- The country is defined by the IP address of the user. 

- Changing the state of the light bulb should be displayed to all users in the room without reloading the page as soon as possible

##### The system supports the following role model:

| Role     | Available actions                  
| -------- |---------------------------       
| `USER`   | Create a new room         
|          | Display a list of rooms 
|          | Display details of room and edit light status 

#### There are the following pages in the system:

| Page               | URL               | Description                                  | Available actions                   
| ------------       |------------------ | -----------------------------------------    | ---------------------------------------------------    
| `Main page`        | */*               | Main page                                    | Login to the system
| `List`             | */room/allRooms*  | List of rooms                                | Display list of all rooms
| `Edit`             | */edit/{id}*      | Viewing details of the room and edit room    | Change light status
| `New`              | */room/new*       | Creating a new room                          | Creating a room from a list of available countries

### Tech stack:
- Java 8
- Spring (Spring Boot, Spring MVC, Spring Data, Spring Security)
- PostgreSQL 
- Thymeleaf
- Jquery
- Maven
- Docker
