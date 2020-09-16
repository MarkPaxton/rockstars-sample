#!/bin/bash
mongoimport --db rockstars --collection songs --jsonArray  --file /app/data/songs.json
