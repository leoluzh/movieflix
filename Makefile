
#start docker - starting database and web database frontend
db-run:
	docker-compose up

#start springboot
sb-run:
    ./mvnw springboot:run