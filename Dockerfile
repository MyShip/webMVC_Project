FROM tomcat:9.0-jdk11-openjdk-slim

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY WebContent/ /usr/local/tomcat/webapps/ROOT/
COPY build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
COPY startup.sh /startup.sh

RUN chmod +x /startup.sh

EXPOSE 8080
CMD ["/startup.sh"]