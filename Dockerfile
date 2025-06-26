FROM tomcat:9.0-jdk11

# 不要なデフォルトアプリケーションを削除
RUN rm -rf /usr/local/tomcat/webapps/*

# 作業ディレクトリ設定
WORKDIR /tmp/build

# 必要なディレクトリを作成
RUN mkdir -p webapp/WEB-INF/classes webapp/WEB-INF/lib

# ソースコードをコピー
COPY . .

# libディレクトリのJARファイルをWEB-INF/libにコピー
RUN if [ -d "lib" ]; then \
    cp lib/*.jar webapp/WEB-INF/lib/; \
else \
    echo "Warning: lib directory not found. Make sure MySQL connector JAR is available."; \
fi

# Javaファイルをコンパイル
RUN if [ -d "src" ]; then \
    find src -name "*.java" -type f > java_files.txt && \
    if [ -s java_files.txt ]; then \
        javac -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/lib/jsp-api.jar:webapp/WEB-INF/lib/*" \
        -d webapp/WEB-INF/classes \
        @java_files.txt; \
    fi; \
fi

# web.xmlをコピー
RUN if [ -f "WebContent/WEB-INF/web.xml" ]; then \
    cp WebContent/WEB-INF/web.xml webapp/WEB-INF/; \
elif [ -f "src/main/webapp/WEB-INF/web.xml" ]; then \
    cp src/main/webapp/WEB-INF/web.xml webapp/WEB-INF/; \
fi

# JSPと静的リソースをコピー
RUN if [ -d "WebContent" ]; then \
    # WebContentのすべてのファイルをwebappルートにコピー（WEB-INF以外）
    find WebContent -type f \( -name "*.jsp" -o -name "*.html" -o -name "*.css" -o -name "*.js" -o -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.woff*" -o -name "*.ttf" \) \
    -exec bash -c 'file="$1"; target="webapp/${file#WebContent/}"; mkdir -p "$(dirname "$target")"; cp "$file" "$target"' _ {} \; && \
    # WebContentの直下のファイルをwebappルートにコピー
    find WebContent -maxdepth 1 -type f \( -name "*.jsp" -o -name "*.html" -o -name "*.css" -o -name "*.js" \) \
    -exec cp {} webapp/ \; 2>/dev/null || true; \
elif [ -d "src/main/webapp" ]; then \
    # Maven標準構造の場合
    find src/main/webapp -type f \( -name "*.jsp" -o -name "*.html" -o -name "*.css" -o -name "*.js" -o -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.woff*" -o -name "*.ttf" \) \
    -exec bash -c 'file="$1"; target="webapp/${file#src/main/webapp/}"; mkdir -p "$(dirname "$target")"; cp "$file" "$target"' _ {} \; && \
    find src/main/webapp -maxdepth 1 -type f \( -name "*.jsp" -o -name "*.html" -o -name "*.css" -o -name "*.js" \) \
    -exec cp {} webapp/ \; 2>/dev/null || true; \
fi

# コピー結果をデバッグ表示
RUN echo "=== Files in webapp root ===" && ls -la webapp/ && \
    echo "=== Files in webapp/WEB-INF ===" && ls -la webapp/WEB-INF/ 2>/dev/null || true

# META-INFがある場合はコピー
RUN if [ -d "META-INF" ]; then \
    cp -r META-INF webapp/; \
fi

# WARファイルを作成してデプロイ
RUN cd webapp && \
    jar -cf /usr/local/tomcat/webapps/ROOT.war . && \
    ls -la /usr/local/tomcat/webapps/

# ポートを公開
EXPOSE 8080

# Tomcatを起動
CMD ["catalina.sh", "run"]
