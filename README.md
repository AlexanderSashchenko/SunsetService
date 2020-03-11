# Sunrise-Sunset Service is a test task for DataX company

    The service allows to get sunrise and sunset time for Ukrainian cities. The service
    communicates to https://sunrise-sunset.org/api and gets required data.
    
#### Technical details:
    - Used Spring Boot and Maven for project setup;
    - Spring Data and MySQL for persistant data;
    - Spring WebClient for handling http requests;
    - Validation, Lombok, slf4;
    
#### To start and use the application:
    - Clone and open the project;
    - Make sure you have MySql server launched on your computer and create 
        a new schema with the name "city_sunset_time";
    - Make sure your database launches at 3306 port or change the port to required
        at resources/application.properties;
    - Also change database credentials (username and password) to requiered at
        resources/application.properties;
    - Now you may run the application. Available services are:
        1) GET http://localhost:8080/city - returns all cities(DTO's) from the 
            database - intended to show all the cities handled by application 
            on front-end;
        2) POST http://localhost:8080/city with the example body:
        {"name":"Львов", "latitude":"49.8397", "longitude":"24.0297"}
        adds new city to the service;
        3) GET http://localhost:8080/event-time - returns all cities(DTO's) from the 
        database - intended to show either sunrise or sunset or both parameters 
        of the DTO on front-end;
    - Validation rules for inputed values:
        No duplicates for the city names.
        Only numbers from -90 to 90 for latitude.
        Only numbers from -180 to 180 for longitude.