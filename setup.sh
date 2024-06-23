#!/bin/bash

# Nazwa kontenera MySQL
CONTAINER_NAME="horses-database"

# Sprawdź, czy kontener istnieje
if docker ps -a --format '{{.Names}}' | grep -Eq "^${CONTAINER_NAME}\$"; then
    echo "Container '${CONTAINER_NAME}' exists. Starting database..."
    docker start "${CONTAINER_NAME}"
    # Wykonaj migracje Django
    echo "Making migrations..."
    python manage.py makemigrations
    sleep 3
    echo "Applying migrations..."
    python manage.py migrate

else
    echo "Container '${CONTAINER_NAME}' does not exist. Creating and starting the container..."
    docker run --name "${CONTAINER_NAME}" -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=horses-database -p 3306:3306 -d mysql:8.0
    echo "Waiting for MySQL to initialize..."
    sleep 30  # Poczekaj na pełną inicjalizację MySQL

    # Wykonaj migracje Django
    echo "Making migrations..."
    python manage.py makemigrations
    sleep 3
    echo "Applying migrations..."
    python manage.py migrate

    # Wgraj dane z insert_data.sql
    echo "Inserting data..."
    docker cp sql_scripts/insert_data.sql "${CONTAINER_NAME}":/initialize.sql
    docker exec horses-database /bin/sh -c 'mysql -u root -ppassword </initialize.sql'
    echo "Finishing..."
fi

# Wyświetl aktualnie uruchomione kontenery
echo "Available containers:"
docker ps
