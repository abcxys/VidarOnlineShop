### Why create four sequences when initializing DB in V1 statement?

### How to enter docker container and postgres command line tool?
```shell
docker exec -it postgresVidar bash
psql -U postgres
```

### Steps to initialize postgresql database
```sql
CREATE DATABASE hardwoodfloor;
ALTER USER postgres WITH PASSWORD 'root';
\c hardwoodfloor;         //Enter the database
\i /home/migration/V1__Init_DB.sql  //You need to move the scripts in docker system directory before running them
```
Run table initialization scripts in docker shell before running springboot

### Why Hibernate throws "Perfume not mapped" error when refract "Perfume" class to "HardwoodFloor" class?
Because Hibernate requires type name in SQL queries match the name of Java class.
Change ```SELECT perfume FROM Perfume perfume...``` to ```SELECT perfume FROM HardwoodFloor perfume... ```

### How to delete a database in POSTGRESQL? And what configuration to change if the database name is changed?
To delete a databased, ```drop database [name]```
If the database name is changed, change the setting in "application.properties"
```spring.datasource.url=jdbc:postgresql://localhost/[database name]```

### Hibernate JPA findById interface returns Optional class
To get entity object, use Optional object.get() to achieve.