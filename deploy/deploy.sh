#!/bin/bash

source /etc/profile

function log() {
    echo `date '+%Y-%m-%d %H:%M:%S'` "$1"
}

function doStop() {
	log "bein to stop doe."
	
	count=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|wc -l`

	if [ $count -gt 0 ]
	then
		log "bein to shutdown doe."
		pid=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|awk  '{ print $2 }'`
		kill $pid
		sleep 3s
	fi
	
	count=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|wc -l`
	if [ $count -gt 0 ]
	then
		log "bein to force to kill doe."
		pid=`ps aux|grep "java -jar dubbo-doe" |grep -v grep|awk  '{ print $2 }'`
		kill -9 $pid
		sleep 3s
	fi
	log "finish stop doe."
}
function doStart() {
        log "bein to install doe."

        java -jar dubbo-doe.jar --spring.profiles.active=prd &

        log "finish install doe."
}

function main() {

	log "welcome to doe."
	
	option="$1"
	
	if [ "$option" = "start" ] 
	then 
		doStart
	elif [ "$option" = "stop" ]
	then
		doStop
	elif [ "$option" = "reload" ]
	then
	    doStop
	    sleep 3s
	    doStart
	elif [ "$option" = "republish" ]
	then
	    doStop
	    cp pom.xml.backup pom.xml
	    rm -rf ./lib/*
	    sleep 3s
	    doStart
	else 
		log "input option error (start/stop/reload/republish)"
	fi
	
	log "done."

}

main "$1"