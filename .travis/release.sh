#!/usr/bin/env bash

set -e

echo "Deploying release version to SONATYPE"
mvn clean deploy --settings .maven.xml -DskipTests=true -Dmaven.javadoc.skip=true -B -U -Prelease




