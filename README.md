# Web Project

## Requirements

### postgres

For running the project you should have a postgres database with `web_project` db. you can setup a postgres using following command:
```bash

docker run --name postgres_container -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 -v postgres_data:/var/lib/postgresql/data postgres
```

### RabbitMQ
Also if you want use rabbit as a queue for calling external apis, you should setup a rabbit server. you can do it by run this command:
```bash

```

