#!/bin/sh
# wait-for-grid.sh
# Wait for the Selenium Grid Hub to be ready before running tests

set -e


HOST=${HUB_HOST:-selenium-hub}
PORT=4444


# Wait for the Grid Hub to be available (Selenium Grid 4+ uses /status)
until curl -s "http://$HOST:$PORT/status" | grep -q '"ready":true'; do
  echo "Waiting for Selenium Grid Hub at $HOST:$PORT..."
  sleep 2
done

echo "Selenium Grid Hub is ready!"

# Wait for at least one Chrome node to be registered and available
while true; do
  NODES=$(curl -s "http://$HOST:$PORT/status" | grep -o 'chrome' | wc -l)
  if [ "$NODES" -ge 1 ]; then
    echo "At least one Chrome node is registered."
    break
  else
    echo "Waiting for Chrome node to register with Selenium Grid..."
    sleep 2
  fi
done

# Run the test command passed as arguments
exec "$@"
