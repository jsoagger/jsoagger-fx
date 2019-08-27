#!/usr/bin/env bash

set -e

echo "About to deploy snapshot version"
mvn clean deploy --settings .maven.xml -DskipTests=true -B -U -Prelease



