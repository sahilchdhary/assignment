This project is about dashboard for Football Game. This project uses Spring boot, lombok and is written in Java language.


## Installation
The application can be run by executing java -jar target/dashboard-0.0.1-SNAPSHOT.jar from the root directory of the project. Or it can be run through any IDE, after importing it as a maven project.
Although i have pushed the jar in the repo, if you want to recreate it, you can run mvn clean package.

## Assumptions
The requirements in the project were very clear, but i have made the following assumptions during the implementation.
1) Multiple goals can happen within a minute
2) Team name and all commands are case sensitive.
3) Goal minutes, entered during update score command, only accepts a positive non-decimal number

Provided if i had more time, i would have preferred to use JPA to persist the game details.
