## Biss job project - IP REST API
This project connects to open source [REST IP API](https://ipapi.co/), saves IP that is searched for in local database and shows saved data with HTTP GET request.

## Tech/framework used

- [Springboot](https://spring.io/projects/spring-boot)
- [Hibernate](http://hibernate.org/)
- [H2Database](https://www.h2database.com)
- [Maven](https://maven.apache.org/)
- [Postman](https://www.getpostman.com/)

## Features

- Add IP and country name to local database from [REST IP API](https://ipapi.co/)
- Read from local database
- Delete saved data from local database
- Error response from external API handled
- Invalid IP requests handled
- Caching of data handled
- Working endpoint which return the number of requests received(successful and unsuccessful)

## Installation
Load project in your favourite IDE (made using Eclipse), hit "Run java application" on "biss.Main" class. App should be up and running on localhost:8080 using H2 in memory database. App was written using Hibernate and all Hibernate supported databases can be used. 

Example of using MSSQL:

Create local MSSQL database called "ip_api". Change "application.properties" file content to(example):

	spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
	spring.datasource.url=jdbc:sqlserver://localhost;databaseName=ip_api
	spring.datasource.username=sa
	spring.datasource.password=pass
	spring.jpa.show-sql=true
	spring.jpa.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect
	spring.jpa.hibernate.ddl-auto = create

Change your custom username and password, give your username admin rights and you are good to go.


## How to use?

In {IP} put desired IP.

Add IP to database/read IP from database:
GET "localhost:8080/country?ip={IP}"

Remove IP from database:
DELETE "localhost:8080/ip/{IP}"

Get number of successful requests:
GET "localhost:8080/requests/successful"

Get number of unsuccessful requests:
GET "localhost:8080/requests/unsuccessful"


## Connect to database

While app is running you can easily connect to H2 database. 
On you favourite browser that supports JavaScript go to: [H2Console](http://localhost:8080/h2-console)

Connect using:

JDBC URL: jdbc:h2:mem:ip_api

User Name : sa

Password:

## License
No license - Created by © Marko Orlović
