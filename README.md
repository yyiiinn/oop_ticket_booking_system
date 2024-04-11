# oop_ticket_booking_system

## Project Prerequisite

### MAVEN

Ensure `Maven` is installed.

If `Maven` has not been installed locally please click [here](https://www.baeldung.com/install-maven-on-windows-linux-mac) to setup `Maven`

### Postgres

To get a postgres you may click [here](https://neon.tech/) to get a free fully managed serverless Postgres

**OR**

If you have Docker installed in your computer you may run a local instance of it by following the guide [here](https://www.docker.com/blog/how-to-use-the-postgres-docker-official-image/)

### Email Client

To use the email client, you have to configure Spring Mail in application.properties. 
Add the hostname of your mail server (e.g., smtp.gmail.com for Gmail) in `spring.mail.host`
Add the port for your chosen mail server (e.g., 587 for Gmail's SMTP) in `spring.mail.port`
Add the email address for your email in `spring.mail.username` and password in `spring.mail.password` (recommended to not include actual password but use other forms such as an app password)
Rest of the configurations are stated in application properties

### application.properties

Create a file called `application.properties` in `src/main/resources` and copy below into your new created file.

```
spring.datasource.url=jdbc:postgresql://<POSTGRES_URL>/<POSTGRES_DATABASE_NAME>
spring.datasource.username=<POSTGRES_USERNAME>
spring.datasource.password=<POSTGRES_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.datasource.hikari.minimum-idle= 2
spring.datasource.hikari.maximum-pool-size= 5
spring.mail.host=smtp.<EMAIL_CLIENT_DOMAIN>.com
spring.mail.port=587
spring.mail.username=<EMAIL_ADDRESS>
spring.mail.password=<EMAIL_ADDRESS_PASSWORD>
spring.mail.properties.debug=true
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**IMPORTANT**

Please replace `<SOME_ENVIRONMENT_NAME>` with the appropriate environment variable

## How to run

Enter to the root directory of the project on your desired CLI and run using the following command

```
mvn spring-boot:run
```

Go to your desired browser and go to `localhost:8080` or click [here](https://localhost:8080/)

## User Details

On intial run, 3 users should be created and the credentials are as follows

| Username             | Password |    Role |
| :------------------- | :------: | ------: |
| user@dispostable.com |   user   |    User |
| officer              | officer  | Officer |
| manager              | manager  | Manager |
