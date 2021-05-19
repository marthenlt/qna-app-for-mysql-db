# QnA Application
This is a modified QnA application with has been ported from MongoDB to MySQL.
The original QnA application running on MongoDB is located at https://github.com/marthenlt/ask-gpdi-tomotius-sg

Framework stacks:
- UI: jQuery Mobile
- Backend: Spring Data JPA, Spring Web MVC and RESTful. These are part of Spring Boot Starter Libs. Refer to the pom.xml file for more info.
- DB: MySQL

Update:
22 Apr 2021: 
- Add JFR (Java Flight Recording) JVM options to capture GC paused time & CPU & Memory utilisation.
- `c2-with-jfr.sh` is shell sript to run the application and capture JFR on standard C2 JIT 
- `graal-with-jfr.sh` is shell sript to run the application and capture JFR on standard Graal JIT 

Thanks!

-Marthen Luther-

(15 Aug 2020)
(22 Apr 2021)
