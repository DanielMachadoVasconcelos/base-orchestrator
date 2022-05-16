#!/usr/bin/env bash

set -e
JAR=$1

while [[ "$(curl -k -s -o /dev/null -w ''%{http_code}'' http://elasticsearch:9200/_cluster/health?wait_for_status=yellow&timeout=2s)" != "200" ]]; do
  >&2 echo "Sales - Elasticsearch not started. Will sleep for 10 seconds."
  sleep 10
done

java -jar ${JAR}