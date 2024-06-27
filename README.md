# Web Project

## Requirements

### PostgreSQL

For running the project you should have a postgres database with `web_project` db. you can setup a postgres using following command:
```bash
docker run --name postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=web_project -d -p 5432:5432 -v ./postgres_data:/var/lib/postgresql/data postgres
```

### RabbitMQ
Also if you want to use rabbit as a queue for calling external apis, you should setup a rabbit server. you can do it by run this command:
```bash
docker run -it --rm -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management
```

