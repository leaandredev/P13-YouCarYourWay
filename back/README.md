# YourCarYourWay App (Back-end)

This is YourCarYourWay App, used to handle rental car.

Actually, the project only contain a POC for the chat feature.

## Features

- Register and log users to connect to the application
- Handle posts:
  - Feed with all posts user is registered for
  - Create and display post details
- Handle topics to subscribe
- Handle user profile

## Installation

After the repository has been imported (see README in the front part of the project), go in back directory and install depencies with maven :

```
mvn install
```

### Create the Mdd Database

1. **Set Up the Database**  
   Open a MySQL client in your terminal or use a tool like MySQL Workbench, and execute the following commands. Replace `mdd_db`, `your_user_name`, and `your_password` with your desired database name, username, and password:

   ```sql
   CREATE DATABASE mdd_db;
   CREATE USER 'your_user_name'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON mdd_db.* TO 'your_user_name'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. **Create Tables**  
   Locate the SQL script in the project, `back/src/main/resources/data.sql`, and execute it in your MySQL client to create the required tables.

3. **Configure Database Access in the API Project**  
   The application uses environment variables to define database parameters. In the `src/main/resources/application.properties` file, ensure the following lines are present:

   ```properties
   # MySQL Database Configuration
   spring.datasource.url=${MYSQL_DATABASE_URL}
   spring.datasource.username=${MYSQL_DATABASE_USERNAME}
   spring.datasource.password=${MYSQL_DATABASE_PASSWORD}
   ```

4. **Set Environment Variables**  
   Configure the environment variables for your operating system:

   - **On Linux/macOS:**  
     Add the following lines to your `~/.bashrc` (or `~/.zshrc` for macOS with zsh) file:

     ```bash
     export MYSQL_DATABASE_URL=jdbc:mysql://localhost:3306/mdd_db
     export MYSQL_DATABASE_USERNAME=your_user_name
     export MYSQL_DATABASE_PASSWORD=your_password
     ```

     Apply the changes:

     ```bash
     source ~/.bashrc
     ```

   - **On Windows (Command Prompt):**  
     Set the environment variables temporarily for the current session:

     ```cmd
     set MYSQL_DATABASE_URL=jdbc:mysql://localhost:3306/mdd_db
     set MYSQL_DATABASE_USERNAME=your_user_name
     set MYSQL_DATABASE_PASSWORD=your_password
     ```

     For persistent variables, use the _System Properties_ panel in Windows to add them under _Environment Variables_.

   - **On Windows (PowerShell):**  
     Use the following commands to set variables for the current session:
     ```powershell
     $env:MYSQL_DATABASE_URL="jdbc:mysql://localhost:3306/mdd_db"
     $env:MYSQL_DATABASE_USERNAME="your_user_name"
     $env:MYSQL_DATABASE_PASSWORD="your_password"
     ```

### Run the server

Run the Spring Boot application in your IDE or in the terminal using maver:

```
mvn spring-boot:run
```

## Usage

The API will be available on your local machine at http://localhost:8080/

## Testing

Toget unit and integration tests, you can run :

```
mvn clean verify
```

## Contributing

MDD App is an open source project. Feel free to fork the source and contribute with your own features.

## Authors

- Lea ANDRE

## Licensing

This project was built under the Creative Commons licence.
