# Trello Clone
A Full Stack clone of Trello with Java Spring, HTML, CSS and vanilla JavaScript

## Description
Trello Clone is a fully functioning task management Web Application. This app support personal Task Board, allowing simple and organized task tracking that separates overarching goals or topics from specific tasks. It has minimal and clean UI that easy to use for everyone.

## Features
 - Register and Login. 

	- Currently, our project only accept email register. 
 
	- We will implement another Authentication (Facebook, Github,...) in the future
 
 - Update account information (name, date of birth, gender, password)
 
 - Board

	- Create personal board

	- Add member to your board
	
	- Recently viewed boards

	- Search all board you have

	- Change list of card in each board

	- Use WebSocket for automatically update your board when someone change card in board

	- Display live notification when a card is assigned to you

## Installation
 - Install Java 8
 - Install [MySQL Workbench](https://www.mysql.com/products/workbench/) and set user=root and password=123456 and create database named TrelloDB
 - Install [Spring Tool Suite IDE](https://spring.io/tools) or [Eclipse IDE](https://www.eclipse.org/downloads/packages/installer)
 - Clone this repository
```
    git clone https://gitlab.com/soict-it4409/20202/nhom13/cnweb
```
 - Open IDE and import this project to workspace
 - Click run button on IDE's tab bar
 - Frontend should be running on http://localhost:8080/ (you have to make this port clear before run this project)
 - Change ddl-auto in application.yml file from "create-drop" to "update" if you want store data in database for future run

## Some Restful API in backend
 - Get all board of user by email: http://localhost:8080/board/title/{email}
 - Get all information of user by email: http://localhost:8080/api/user/{email}
 - Change date of birth of user: http://localhost:8080/api/user/dob/{gmail}

## Screenshot
 - Landing page
![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-22%2017-08-59.png?raw=true)

![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-22%2017-09-54.png?raw=true)

![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-22%2017-10-06.png?raw=true)
 

 - Login page
![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-22%2017-10-18.png?raw=true)


 - Board page
![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-22%2017-11-21.png?raw=true)

 - Visit a board that you do not have permission
![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-23%2023-22-00.png?raw=true)

 - Forgot password page
 
![Alt text](https://github.com/dinhhh/TrelloClone/blob/master/TrelloWebApp/src/main/resources/static/image/Screenshot%20from%202021-05-23%2023-24-36.png?raw=true)

## License
MIT
