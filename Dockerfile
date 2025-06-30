FROM tomcat:9.0-jdk11-openjdk-slim

# 作業ディレクトリ設定
WORKDIR /usr/local/tomcat

# WARファイルをコピー
COPY target/My_Notes.war /usr/local/tomcat/webapps/

# Tomcatのメモリ設定
ENV CATALINA_OPTS="-Xms256m -Xmx512m"

# ポート設定
EXPOSE 8080

# Tomcat起動
CMD ["catalina.sh", "run"]