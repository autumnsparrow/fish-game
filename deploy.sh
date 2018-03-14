#!/bin/sh
PS3="deploy fish-app# "

PAYMENT_REMOTE_SHELL='ssh dev@payment.toyo.cool'

p_shell(){
	$PAYMENT_REMOTE_SHELL $1
}
payment_backup(){
	p_shell 'ls -la /home/dev;'	
	echo "\r\n"
	echo $1
}


select action in payment-backup payment-upload payment-deploy exit

do 
	case $action in
		payment-backup)
			echo "payment backup\r\n"
			read cmd
			echo "\r\n"
			payment_backup $cmd
			;;
		payment-upload)
			echo "payment upload"
			;;
		payment-deploy)
			echo "payment deploy"
			;;
		exit)
			echo "exit"
			break;;
	esac
done
