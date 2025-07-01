# Tomcatベースのイメージを使う
FROM tomcat:9.0

# WARファイルをTomcatのwebappsにコピー
COPY My_Notes.war /usr/local/tomcat/webapps/

# ポート解放
EXPOSE 8080

# Tomcat起動（CMDは既にベースイメージで定義済み）