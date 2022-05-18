#!/usr/bin/env bash

set -e
JAR=$1

while [[ "$(curl -k -s -o /dev/null -w '%{http_code}\n' 'http://elasticsearch:9200/_cluster/health?wait_for_status=yellow&timeout=2s')" != "200" ]]; do
  >&2 echo "Integration Test - Elasticsearch not started. Will sleep for 5 seconds."
  sleep 5
done

>&2 echo "Integration Test - Elasticsearch has started. Up and running."

while [[ "$(curl -k -s -o /dev/null -w '%{http_code}\n' 'http://sales:8082/actuator/health/readiness')" != "200" ]]; do
  >&2 echo "Integration Test - Sales not started. Will sleep for 5 seconds."
  sleep 45;
done

>&2 echo "Integration Test - Sales has started. Up and running."

java -jar ${JAR}