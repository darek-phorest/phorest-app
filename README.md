# PhorestApp backend

## Instructions:
### Build application:

```
gradle build -x test
```

### Run unit tests:

```
gradle test
```

### Run integration tests:

```
gradle integrationTest
```

- integration Tests require Docker

### Running application in docker mode:

Run application with spring profile `docker`. This can be achieved by:

- Providing active profile in spring boot run config
- Adding this: `-Dspring.profiles.active=docker` to vm options.

Run local postgres database in docker container:

```
docker-compose -f local-stack.yml up --force-recreate --build -d phorest-app-postgres
```

### Running application in inmemory mode:

Run application with spring profile `inmemory`. This can be achieved by:

- Providing active profile in spring boot run config
- Adding this: `-Dspring.profiles.active=inmemory` to vm options.

### Running application as docker container (http://localhost:8080/):

```
docker-compose -f local-stack.yml up --force-recreate --build
```

