#!/bin/bash

SCRIPT_PATH=`dirname $0`

# no arguments passed, default desktop mode
if [[ $# -eq 0 ]]
	then
    	SPRING_PROFILE="desktop"
	else
		SPRING_PROFILE=$1
fi

echo "Running in $SPRING_PROFILE" 
if [[ -z "$PATH_TO_FX" ]]
then
	echo 'PATH_TO_FX must be set'
	echo '[ERROR] JavaFX runtime is not set, please configure your environment'
	echo 'Following url explains how to set JavaFX environment: https://openjfx.io/openjfx-docs/#install-javafx'
	exit 1
fi


java -Dspring.profiles.active=$SPRING_PROFILE --module-path=$PATH_TO_FX -jar $SCRIPT_PATH/bin/jsoagger-jfxcore-demoapp.jar

