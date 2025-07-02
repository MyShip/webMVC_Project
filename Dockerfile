FROM tomcat:9.0

COPY My_Notes.war /usr/local/tomcat/webapps/ROOT.war
COPY server.xml /usr/local/tomcat/conf/server.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]