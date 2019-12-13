#!/bin/bash
siege -t 120S --content-type "application/json" 'http://localhost:4003/api POST {"chatId":1337,"message":"/hilfe","firstName":"Test","lastName":"User","userName":null,"title":null,"chatType":"Private"}'
