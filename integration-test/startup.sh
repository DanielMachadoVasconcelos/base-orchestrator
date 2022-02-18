#!/usr/bin/env bash

set -e
JAR=$1

while [[ "$(curl -k -s -o /dev/null -w ''%{http_code}'' http://sales:8082/actuator/health)" != "200" ]]; do
  >&2 echo "Integration Test - Sales not started. Will sleep for 2 seconds."
  sleep 2
done

java -jar ${JAR}