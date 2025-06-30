FROM tomcat:9.0-jre11-slim

# 最小限のメモリ設定
ENV JAVA_OPTS="-Xmx64m -Xms32m -Djava.awt.headless=true"

# 既存のwebappsを削除
RUN rm -rf /usr/local/tomcat/webapps/*

# WARファイルをコピー（事前にEclipseで作成）
COPY your-app.war /usr/local/tomcat/webapps/ROOT.war

# ポートを公開
EXPOSE 8080

# Tomcatを起動
CMD ["catalina.sh", "run"]