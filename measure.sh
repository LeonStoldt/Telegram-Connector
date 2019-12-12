#!/bin/bash

echo How often do you want to curl?
read TIMES

for i in {1..$TIMES}
do
 
  curl -H 'Content-Type: application/json' localhost:8079/api -d '{"chatId":1337,"message":"/info","firstName":"Test","lastName":"User","userName":null,"title":null,"chatType":"Private"}' -s -o /dev/null -w '%{time_starttransfer}\n' "$@"

done
