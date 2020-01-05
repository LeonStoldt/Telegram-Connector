#!/bin/sh
# Important! You need to copy this file to the parent folder of this project to run it successfully.
WORKINGDIR=$(pwd)

git config --global credential.helper cache
git config --global credential.helper 'cache --timeout=3600'

git clone https://github.com/LeonStoldt/Telegram-Connector.git
git clone https://github.com/LeonStoldt/Distribution-Service.git
git clone https://github.com/LeonStoldt/Nordbahn-Service.git
git clone https://github.com/LeonStoldt/Translate-Service.git
git clone https://github.com/LeonStoldt/URL-Shortener-Service.git
git clone https://github.com/LeonStoldt/Weather-Service.git
git clone https://github.com/LeonStoldt/Wikipedia-Service.git

cd $WORKINGDIR/Telegram-Connector && git pull && \
cd $WORKINGDIR/Distribution-Service && git pull && \
cd $WORKINGDIR/Nordbahn-Service && git pull && \
cd $WORKINGDIR/WeatherS-ervice && git pull && \
cd $WORKINGDIR/Translate-Service && git pull && \
cd $WORKINGDIR/URL-Shortener-Service && git pull  && \
cd $WORKINGDIR/Wikipedia-Service git pull && \

mvn -f $WORKINGDIR/Telegram-Connector/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/Distribution-Service/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/Nordbahn-Service/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/Weather-Service/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/Translate-Service/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/URL-Shortener-Service/pom.xml -Dmaven.test.skip=true package && \

cp $WORKINGDIR/Telegram-Connector/docker-compose.yaml $WORKINGDIR/docker-compose.yaml && \
cp $WORKINGDIR/Telegram-Connector/haproxy.cfg $WORKINGDIR/haproxy.cfg && \

cd $WORKINGDIR
docker-compose down && \
docker-compose up --build --force-recreate
