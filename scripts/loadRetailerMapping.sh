#!/bin/bash
#set -x

####################
#
# Script to import a saved retailer to SIQ Catalog CVS file
# into retailer BigQuery enviroment in standard location
#
####################

function usage() {
	local MSG=$1
	echo ""
	echo "Usage: $0 PROJECT_NAME FILE_NAME"
	if [ ! -z "$MSG" ]; then
		echo "  "$MSG
	fi
	exit 1
}

PROJECT_NAME=$1
FILE_NAME=$2

## invoke usage if two arguments are not supplied
[[ $# -ne 2 ]] && usage "Wrong number of arguments!"

## invoke usage if project_name doesn't end in "-siq"
[[ ! $PROJECT_NAME =~ -siq$ ]] && usage "Project name should end in -siq"

## invoke usage if file_name doesn't end in ".csv"
[[ ! $FILE_NAME =~ \.csv$ ]] && usage "Filename should end in *.csv"

## invoke usage if bq binary not found
[[ -z $(which bq) ]] && usage "BQ binary is not found"

bq load --replace --skip_leading_rows=1 ${PROJECT_NAME}:siq.retailerMap ${FILE_NAME} "retailerId:STRING,siqId:STRING"
