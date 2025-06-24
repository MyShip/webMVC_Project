# OpenJDK 11を使用
FROM openjdk:11-jdk-slim

# Tomcatをインストール
RUN apt-get update && apt-get install -y wget && \
    wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.65/bin/apache-tomcat-9.0.65.tar.gz && \
    tar -xzf apache-tomcat-9.0.65.tar.gz && \
    mv apache-tomcat-9.0.65 /opt/tomcat && \
    rm apache-tomcat-9.0.65.tar.gz

# 作業ディレクトリ設定
WORKDIR /app

# ソースコードをコピー
COPY . .

# JavaコンパイルとWAR作成のためのスクリプト
RUN mkdir -p WEB-INF/classes && \
    find src -name "*.java" -exec javac -cp "lib/*" -d WEB-INF/classes {} + && \
    jar -cf app.war -C . WEB-INF && \
    cp app.war /opt/tomcat/webapps/

# ポート公開
EXPOSE 8080

# Tomcat起動
CMD ["/opt/tomcat/bin/catalina.sh", "run"]