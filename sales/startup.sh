#!/usr/bin/env bash

set -e
JAR=$1

while [[ "$(curl -k -s -o /dev/null -w ''%{http_code}'' http://localhost:9200/_cluster/health?wait_for_status=yellow&timeout=2s)" != "200" ]]; do
  >&2 echo "Sales - Elasticsearch not started. Will sleep for 2 seconds."
  sleep 2
done

java -jar ${JAR}