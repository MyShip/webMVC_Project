FROM tomcat:9.0-jdk11

# 作業ディレクトリ設定
WORKDIR /app

# ソースコードをコピー
COPY . .

# WEB-INFディレクトリ構造を作成
RUN mkdir -p /app/WEB-INF/classes

# Servlet APIを含むクラスパスでコンパイル
RUN find src -name "*.java" -exec javac \
    -cp "$CATALINA_HOME/lib/servlet-api.jar:$CATALINA_HOME/lib/jsp-api.jar:lib/*" \
    -d /app/WEB-INF/classes {} +

# web.xmlがある場合はコピー
RUN if [ -f "WebContent/WEB-INF/web.xml" ]; then \
        cp WebContent/WEB-INF/web.xml /app/WEB-INF/; \
    elif [ -f "src/main/webapp/WEB-INF/web.xml" ]; then \
        cp src/main/webapp/WEB-INF/web.xml /app/WEB-INF/; \
    fi

# JSPファイルなどの静的リソースをコピー
RUN if [ -d "WebContent" ]; then \
        cp -r WebContent/* /app/ && \
        # WEB-INFは後で再コピーするので除外して、静的ファイルを確実にルートに配置
        find WebContent -type f \( -name "*.html" -o -name "*.css" -o -name "*.js" -o -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.woff*" -o -name "*.ttf" \) -exec cp --parents {} /app/ \; ; \
    elif [ -d "src/main/webapp" ]; then \
        cp -r src/main/webapp/* /app/ && \
        find src/main/webapp -type f \( -name "*.html" -o -name "*.css" -o -name "*.js" -o -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.woff*" -o -name "*.ttf" \) -exec cp --parents {} /app/ \; ; \
    fi

# libディレクトリがある場合はWEB-INF/libにコピー
RUN if [ -d "lib" ]; then \
        mkdir -p /app/WEB-INF/lib && \
        cp lib/* /app/WEB-INF/lib/; \
    fi

# WARファイルを作成してTomcatにデプロイ
RUN cd /app && \
    jar -cf ROOT.war WEB-INF && \
    if [ -d "META-INF" ]; then jar -uf ROOT.war META-INF; fi && \
    find . -name "*.jsp" -o -name "*.html" -o -name "*.css" -o -name "*.js" | \
    xargs -r jar -uf ROOT.war && \
    cp ROOT.war $CATALINA_HOME/webapps/

# 不要なファイルを削除
RUN rm -rf $CATALINA_HOME/webapps/ROOT

EXPOSE 8080

CMD ["catalina.sh", "run"]