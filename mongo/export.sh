#!/bin/bash
echo Exporting commentMongo...
mongoexport -d test -c commentMongo --out mongo/commentMongo.json

echo Exporting projectIdeaMongo...
mongoexport -d test -c projectIdeaMongo --out mongo/projectIdeaMongo.json

echo Exporting solutionMongo...
mongoexport -d test -c solutionMongo --out mongo/solutionMongo.json

echo Export finished!
exit 0
