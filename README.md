# Web Project

## Build

run:

```bash
docker build -t weather -f Dockerfile-weather .
```
```bash
docker build -t user-management -f Dockerfile-user-management .
```
```bash
docker build -t frontend -f Dockerfile-frontend .
```

after that run this command:

```bash
docker compose up -d
```

