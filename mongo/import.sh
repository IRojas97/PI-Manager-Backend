#!/bin/bash
echo Importing commentMongo...
mongoimport -d test -c commentMongo --drop --file mongo/commentMongo.json

echo Importing projectIdeaMongo...
mongoimport -d test -c projectIdeaMongo --drop --file mongo/projectIdeaMongo.json

echo Importing solutionMongo...
mongoimport -d test -c solutionMongo --drop --file mongo/solutionMongo.json

echo Import finished!
exit 0
