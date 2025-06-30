#!/bin/bash
export JAVA_OPTS="-Xms128m -Xmx400m"
export CATALINA_OPTS="-server -Xms128m -Xmx400m"
exec /usr/local/tomcat/bin/catalina.sh run