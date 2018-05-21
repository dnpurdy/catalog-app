#!/bin/bash
#set -e

##SERVER="http://localhost:8080"
SERVER="https://catalog-dot-swiftiq-master.appspot.com"

curl -X GET ${SERVER}/catalog-csv > ../catalog/catalog.csv
