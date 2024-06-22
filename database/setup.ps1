# Nazwa kontenera MySQL
$CONTAINER_NAME = "horses-database"

# Sprawdź, czy kontener istnieje
if ((docker ps -a --format '{{.Names}}') -match "^${CONTAINER_NAME}$") {
    Write-Output "Container '${CONTAINER_NAME}' exists. Starting database..."
    docker start "${CONTAINER_NAME}"

    # Wykonaj migracje Django
    Write-Output "Making migrations..."
    python manage.py makemigrations
    Start-Sleep -Seconds 3
    Write-Output "Applying migrations..."
    python manage.py migrate
}
else {
    Write-Output "Container '${CONTAINER_NAME}' does not exist. Creating and starting the container..."
    docker run --name "${CONTAINER_NAME}" -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=horses-database -p 3306:3306 -d mysql:8.0
    Write-Output "Waiting for MySQL to initialize..."
    Start-Sleep -Seconds 30  # Poczekaj na pełną inicjalizację MySQL

    # Wykonaj migracje Django
    Write-Output "Making migrations..."
    python manage.py makemigrations
    Start-Sleep -Seconds 3
    Write-Output "Applying migrations..."
    python manage.py migrate

    # Wgraj dane z insert_data.sql
    Write-Output "Inserting data..."
    docker cp sql_scripts/insert_data.sql "${CONTAINER_NAME}:/initialize.sql"
    docker exec "${CONTAINER_NAME}" /bin/sh -c 'mysql -u root -ppassword </initialize.sql'
    Write-Output "Finishing..."
}

# Wyświetl aktualnie uruchomione kontenery
Write-Output "Available containers:"
docker ps
