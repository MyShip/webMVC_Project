# Tomcatベースのイメージを使う
FROM tomcat:9.0

# WARファイルをTomcatのwebappsにコピー
COPY webapp/My_Notes.war /usr/local/tomcat/webapps/

# ポート解放
EXPOSE 8080
