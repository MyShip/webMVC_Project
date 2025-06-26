FROM tomcat:9.0-jdk11

# 不要なデフォルトアプリケーションを削除
RUN rm -rf /usr/local/tomcat/webapps/*

# 作業ディレクトリ設定
WORKDIR /tmp/build

# ソースコードをコピー
COPY . .

# 必要なディレクトリを作成
RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
             /usr/local/tomcat/webapps/ROOT/WEB-INF/lib

# JARファイルをコピー（libディレクトリから）
RUN if [ -d "lib" ]; then \
    cp lib/*.jar /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/; \
fi

# Javaファイルをコンパイル
RUN if [ -d "src" ]; then \
    find src -name "*.java" -type f > java_files.txt && \
    if [ -s java_files.txt ]; then \
        javac -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/lib/jsp-api.jar:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/*" \
        -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
        @java_files.txt; \
    fi; \
fi

# web.xmlをコピー
RUN if [ -f "WebContent/WEB-INF/web.xml" ]; then \
    cp WebContent/WEB-INF/web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/; \
elif [ -f "src/main/webapp/WEB-INF/web.xml" ]; then \
    cp src/main/webapp/WEB-INF/web.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/; \
fi

# WebContentの内容をすべてROOTにコピー
RUN if [ -d "WebContent" ]; then \
    cp -r WebContent/* /usr/local/tomcat/webapps/ROOT/ 2>/dev/null || true; \
elif [ -d "src/main/webapp" ]; then \
    cp -r src/main/webapp/* /usr/local/tomcat/webapps/ROOT/ 2>/dev/null || true; \
fi

# デバッグ: ファイル構造を確認
RUN echo "=== ROOT directory contents ===" && \
    ls -la /usr/local/tomcat/webapps/ROOT/ && \
    echo "=== Looking for TopPage.html ===" && \
    find /usr/local/tomcat/webapps/ -name "TopPage.html" -type f

# ポートを公開
EXPOSE 8080

# Tomcatを起動
CMD ["catalina.sh", "run"]