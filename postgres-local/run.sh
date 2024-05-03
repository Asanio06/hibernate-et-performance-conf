#!/bin/bash

docker run -d \
    --name hibernate-db \
    -p 5435:5432 \
    -e POSTGRES_USER=hibernate-user \
    -e POSTGRES_PASSWORD=Password1234 \
    -e POSTGRES_DB=hibernate-db \
    -v hibernate-db:/var/lib/postgresql/data \
    postgres:13-alpine