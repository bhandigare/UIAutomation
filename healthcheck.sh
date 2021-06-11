#!/bin/sh
# Environment Variables
# HUB_HOST

echo "Checking if hub is ready - $HUB_HOST"

while [ "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )" != "true" ]
do
	sleep 1
done

# start the maven command
mvn test -DHUB_HOST=$HUB_HOST -DBROWSER=$BROWSER
