docker run -p 5432:5432 --name postgres_pap -e POSTGRES_PASSWORD=root -d postgres
docker cp scripts/init_database.sql postgres_pap:/docker-entrypoint-initdb.d/initialize.sql
docker exec -u postgres postgres_pap psql postgres postgres -f docker-entrypoint-initdb.d/initialize.sql
mvn package
mvn exec:java@db-init