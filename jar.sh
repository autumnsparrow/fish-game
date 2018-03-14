#!/bin/sh
pwd=`pwd`
projects=(game-context fish-config fish-data-wrapper fish-remote-service fish-protocol-beans  fish-game  )
for p in  ${projects[*]}
do
	cd $pwd/$p
	svn ci
#	mvn clean
#	svn status|grep ?|xargs svn add
#	sh svndel.sh
#	svn ci pom.xml src
#	svn update src
#	svn update src
#	mvn -Dmaven.test.skip=true compile install
done
cd $pwd
