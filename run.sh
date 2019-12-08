#!/bin/sh
WORKINGDIR=$(pwd)

git clone https://gitlab2.nordakademie.de/cloud-fundamentals/telegram-connector.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/distributionservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/nordbahnservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/weatherservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/translateservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/urlshortenerservice.git
git clone https://gitlab2.nordakademie.de/cloud-fundamentals/wikipediaservice.git

cd $WORKINGDIR/telegram-connector && git pull
cd $WORKINGDIR/distributionservice && git pull
cd $WORKINGDIR/nordbahnservice && git pull
cd $WORKINGDIR/weatherservice && git pull
cd $WORKINGDIR/translateservice && git pull
cd $WORKINGDIR/urlshortenerservice && git pull
cd $WORKINGDIR/wikipediaservice git pull

mvn -f $WORKINGDIR/telegram-connector/pom.xml package
mvn -f $WORKINGDIR/distributionservice/pom.xml package
mvn -f $WORKINGDIR/nordbahnservice/pom.xml package
mvn -f $WORKINGDIR/weatherservice/pom.xml package
mvn -f $WORKINGDIR/translateservice/pom.xml package
mvn -f $WORKINGDIR/urlshortenerservice/pom.xml package

cp $WORKINGDIR/telegram-connector/docker-compose.yaml $WORKINGDIR/docker-compose.yaml

cd $WORKINGDIR
docker-compose up --build
