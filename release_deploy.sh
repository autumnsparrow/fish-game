#!/bin/sh
pwd=`pwd`
projects=(game-context fish-config fish-data-wrapper fish-remote-service fish-protocol-beans  fish-game  )
targets=(system/node-1 friends/node-1 mail/node-1 user/node-1 zone/node-1)
remote_home=developer@www.toyo.cool:/home/developer/Apps
echo $pwd
for app in ${targets[*]}
do
	echo "sync $1  $remote_home/$app/fish-app/lib"
	rsync -avzh --progress $1 $remote_home/$app/fish-app/lib
done
