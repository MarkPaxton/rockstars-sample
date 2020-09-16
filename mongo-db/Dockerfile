FROM mongo:latest

ENV MONGO_INITDB_DATABASE admin

COPY 01-setup-rockstars.js /docker-entrypoint-initdb.d/01-01-01-setup-rockstars.js
COPY 02-import-rockstars.sh /docker-entrypoint-initdb.d/02-import-rockstars.sh

WORKDIR /app/data
COPY songs.json .

EXPOSE 27017
