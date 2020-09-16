#!/bin/sh
docker rm --force rockstars-test-db
docker build -t rockstars-test-db .
docker run --publish 27017:27017 --name=rockstars-test-db  rockstars-test-db:latest

