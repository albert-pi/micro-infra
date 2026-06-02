#!/bin/bash

SIGNAL=${SIGNAL:-TERM}

PIDS=$(ps -ef | grep microinfra.gateway | grep java | grep -v grep | awk '{print $2}')

if [ -z "$PIDS" ]; then
  echo "No gateway process found"
  exit 1
else
  echo "Found gateway process($PIDS) and stopping it..."
  kill -s $SIGNAL $PIDS
fi

ret=1
for i in {1..300}
do
    ps -ef | grep microinfra.gateway | grep java | grep -v grep >/dev/null 2>&1
    if [ $? -ne 0 ]; then
        echo "gateway($PIDS) was stopped"
        ret=0
        break
    fi
    sleep 1
done

if [ $ret -eq 1 ]; then
    echo "Wait process stop timeout: gateway does not stop for more than 300s!"
fi
exit $ret
