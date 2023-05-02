## Description
Backend application for the pizzeria project developed for Software Technology course at TU Graz. This application is written in TypeScript and uses the [NestJS](https://github.com/nestjs/nest) framework.

## Running the application
To run the application you need to have Node.js installed. You can download Node.js from [here](https://nodejs.org/en/download/). Also, you need to have a PostgreSQL database running. You can use Docker to run the database. See the [Running the database](#running-the-database) section for more information.
After that you can run the following commands:

```bash
npm install # install dependencies
npm run start:dev # start the application in development mode
```

## Running the database
Docker is primary tool used to run the database. You can download Docker from [here](https://www.docker.com/products/docker-desktop).
Once you have Docker installed, you can use the following command to spin up a PostgreSQL database:

```bash
docker run --name postgres \
           -e POSTGRES_USER=postgres \
           -e POSTGRES_PASSWORD=password \
           -e POSTGRES_DB=pizzeria -p 5432:5432 \
           -v pgdata:/var/lib/postgresql/data \
           -d postgres
```

Database connection string is configured in `.env.dev` file. It is already configured to connect to the database running in the Docker container.
If you wish to connect to a different database, you can change the connection string in the `.env.dev` file.

In order to create an initial migration run:

```bash
npx prisma migrate dev --name init
```
