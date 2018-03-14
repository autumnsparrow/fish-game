#!/bin/sh
pwd=`pwd`
select prj in game-context fish-config fish-data-wrapper fish-remote-service fish-protocol-beans  fish-game;
do break;done
cd $prj
mvn clean
#mvn -Dmaven.test.skip=true compile install
cd $pwd

