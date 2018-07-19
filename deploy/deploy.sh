#!/bin/bash

source /etc/profile


function doStop() {
	echo "bein to stop doe."
	
	count=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|wc -l`

	if [ $count -gt 0 ]
	then
		echo "bein to shutdown doe."
		pid=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|awk  '{ print $2 }'`
		kill $pid
		sleep 3s
	fi
	
	count=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|wc -l`
	if [ $count -gt 0 ]
	then
		echo "bein to force to kill doe."
		pid=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|awk  '{ print $2 }'`
		kill -9 $pid
		sleep 3s
	fi
	echo "finish stop doe."
}
function doStart() {
        echo "bein to install doe."

        java -jar dubbo-doe-1.0.0-RELEASE.jar --spring.profiles.active=prd &

        echo "finish install doe."
}

function main() {

	echo "welcome to doe."
	
	option="$1"
	
	if [ "$option" = "start" ] 
	then 
		doStart
	elif [ "$option" = "stop" ]
	then
		doStop
	else 
		echo "input option error (start/stop)"
	fi
	
	echo "done."

}

main "$1"