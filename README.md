# CoffeeShop1
## How to run tests: 
***
### Run tests directly in a file or folder﻿ 
Click the ![](https://resources.jetbrains.com/help/img/idea/2024.1/app-client.expui.run.run.svg) gutter icon next to the test class or test method and select **Run Test** from the list.
***
### Run tests using the Run widget
Use the Run widget on the main toolbar to select the configuration you want to run.
***
### Run single/multiple tests in Maven
* Run all the unit test classes
$ mvn test
* Run a single test class
$ mvn -Dtest=TestApp1 test
* Run multiple test classes
$ mvn -Dtest=TestApp1,TestApp2 test
## docker-compose instructions: 
### Check if the container is running:
* $ docker ps
#### if no, restart 
* $ docker-compose restart db
#### build docker compose yml
* $ docker-compose up --build
