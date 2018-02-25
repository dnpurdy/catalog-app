#!/bin/bash
#set -e

bq load --replace --skip_leading_rows=1 --source_format=CSV dev1-siq:siq.Catalog ../catalog/catalog.csv ../scripts/schema.json
