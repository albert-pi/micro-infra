#!/bin/bash

SIGNAL=${SIGNAL:-TERM}

PIDS=$(ps -ef | grep microinfra.store | grep java | grep -v grep | awk '{print $2}')

if [ -z "$PIDS" ]; then
  echo "No store service process found"
  exit 1
else
  echo "Found store service process($PIDS) and stopping it..."
  kill -s $SIGNAL $PIDS
fi

ret=1
for i in {1..300}
do
    ps -ef | grep microinfra.store | grep java | grep -v grep >/dev/null 2>&1
    if [ $? -ne 0 ]; then
        echo "store service($PIDS) was stopped"
        ret=0
        break
    fi
    sleep 1
done

if [ $ret -eq 1 ]; then
    echo "Wait process stop timeout: store service does not stop for more than 300s!"
fi
exit $ret
