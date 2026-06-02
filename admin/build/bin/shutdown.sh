#!/bin/bash

SIGNAL=${SIGNAL:-TERM}

PIDS=$(ps -ef | grep microinfra.admin | grep java | grep -v grep | awk '{print $2}')

if [ -z "$PIDS" ]; then
  echo "No system administration process found"
  exit 1
else
  echo "Found system administration process($PIDS) and stopping it..."
  kill -s $SIGNAL $PIDS
fi

ret=1
for i in {1..300}
do
    ps -ef | grep microinfra.admin | grep java | grep -v grep >/dev/null 2>&1
    if [ $? -ne 0 ]; then
        echo "system administration process($PIDS) was stopped"
        ret=0
        break
    fi
    sleep 1
done

if [ $ret -eq 1 ]; then
    echo "Wait process stop timeout: system administration process does not stop for more than 300s!"
fi
exit $ret
