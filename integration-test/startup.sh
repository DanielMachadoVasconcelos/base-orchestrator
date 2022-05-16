#!/usr/bin/env bash

set -e
JAR=$1

sleep 15

while [[ "$(curl -k -s -o /dev/null -w ''%{http_code}'' http://elasticsearch:9200/_cluster/health?wait_for_status=yellow&timeout=2s)" != "200" ]]; do
  >&2 echo "Integration Test - Elasticsearch not started. Will sleep for 10 seconds."
  sleep 15
done

sleep 15

while [[ "$(curl -k -s -o /dev/null -w ''%{http_code}'' http://sales:8082/actuator/health)" != "200" ]]; do
  >&2 echo "Integration Test - Sales not started. Will sleep for 15 seconds."
  sleep 15
done

sleep 15

java -jar ${JAR}