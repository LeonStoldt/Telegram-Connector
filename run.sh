#!/bin/sh
WORKINGDIR=$(pwd)

git config --global credential.helper cache
git config --global credential.helper 'cache --timeout=3600'

git clone https://gitlab2.nordakademie.de/cloud-fundamentals/telegram-connector.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/distributionservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/nordbahnservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/weatherservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/translateservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/urlshortenerservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/wikipediaservice.git

cd $WORKINGDIR/telegram-connector && git pull && \
cd $WORKINGDIR/distributionservice && git pull && \
cd $WORKINGDIR/nordbahnservice && git pull && \
cd $WORKINGDIR/weatherservice && git pull && \
cd $WORKINGDIR/translateservice && git pull && \
cd $WORKINGDIR/urlshortenerservice && git pull  && \
cd $WORKINGDIR/wikipediaservice git pull && \

mvn -f $WORKINGDIR/telegram-connector/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/distributionservice/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/nordbahnservice/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/weatherservice/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/translateservice/pom.xml -Dmaven.test.skip=true package && \
mvn -f $WORKINGDIR/urlshortenerservice/pom.xml -Dmaven.test.skip=true package && \

cp $WORKINGDIR/telegram-connector/docker-compose.yaml $WORKINGDIR/docker-compose.yaml && \
cp $WORKINGDIR/telegram-connector/nginx.conf $WORKINGDIR/nginx.conf && \

cd $WORKINGDIR
docker-compose down && \
docker-compose up --build --force-recreate  --scale distributor=10 --scale nordbahn=5 --scale translate=5 --scale urlshortener=5 --scale wikipedia=5 --scale weather=5 --scale mariadb=5