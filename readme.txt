
docker run --name bd-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=riovas -p 5432:5432 -d postgres:18.3

docker run -it --rm --link bd-postgres:postgres postgres:18.3 psql -h bd-postgres -U postgres

	copier/coller du contenu de bd.sql (création et peuplement des relations)

Pour lancer une partie:
POST localhost:8080/debut avec JSON: {"nom" : "alice"}

Pour jouer:
POST localhost:8080/essai avec JSON: {"joueur":{"nom":"alice"}, "essai":"SOLID"}
