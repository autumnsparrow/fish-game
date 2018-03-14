#/!/bin/sh
cd $(pwd)
for app in system/node-1 friends/node-1 mail/node-1 user/node-1 zone/node-1; do sync * developer@www.toyo.cool:/home/developer/Apps/$app/fish-app/lib; done
