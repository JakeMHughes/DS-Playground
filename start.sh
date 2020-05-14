#!/bin/bash

COLOR_RED='\033[0;31m'
COLOR_GREEN='\e[92m'
COLOR_DEFAULT='\033[0m'


function init(){

	#Check docker
	if command -v docker >/dev/null 2>&1; then
		cdocker=1;
	else
		printf "${COLOR_RED}ERROR:${COLOR_DEFAULT} Docker is a required dependency."
		printf "       Stopping...\n"
		exit 1
	fi

	#Validate maven is installed
	if command -v mvn >/dev/null 2>&1; then
		cmvn=1;
	else
		printf "${COLOR_RED}ERROR:${COLOR_DEFAULT} Maven is a required dependency.\n"
		printf "       Stopping...\n"
		exit 1
	fi


}

init;
printf "${COLOR_GREEN}UPDATE:${COLOR_DEFAULT} Finished Initialization.\n"
mvn clean package
printf "${COLOR_GREEN}UPDATE:${COLOR_DEFAULT} Finished maven build.\n"
if [[ $(docker image ls | grep ds-playground/latest) ]]; then
	docker rmi ds-playground/latest
	printf "${COLOR_GREEN}UPDATE:${COLOR_DEFAULT} Finished Removing ds-playground/latest image.\n"
fi
docker build -t ds-playground/latest .
printf "${COLOR_GREEN}UPDATE:${COLOR_DEFAULT} Finished building docker image.\n"
docker run -d -p 8080:8080 --rm --name=ds-playground ds-playground/latest
printf "${COLOR_GREEN}UPDATE:${COLOR_DEFAULT} Finished deploying container.\n"
printf "${COLOR_GREEN}OK...${COLOR_DEFAULT}\n"
