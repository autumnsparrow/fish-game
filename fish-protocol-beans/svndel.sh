#!/bin/sh
DIR=`pwd`
cd $DIR
echo "$DIR"
rm -fr del.log
svn status|grep "\!">del.log
if [ -e del.log ] 
then
	echo 'del exist'
	more del.log|wc -l
	if [ `more del.log|wc -l` -gt 0 ]
	then
		awk 'begin {FS=" "} { print $2 }' del.log |xargs svn del
	fi
	rm -fr del.log
else
	echo "file not exist"
	
fi
svn ci src

