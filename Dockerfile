# ビルドステージ
FROM maven:3.8.4-openjdk-11-slim AS build

WORKDIR /app

# すべてのファイルをコピー
COPY . .

# Mavenプロジェクトかどうかチェックし、ビルド
RUN if [ -f "pom.xml" ]; then \
        mvn clean package -DskipTests; \
    else \
        echo "No pom.xml found. Creating directory structure..."; \
        mkdir -p target; \
        echo "Manual build required"; \
    fi

# 実行ステージ
FROM tomcat:9.0-jdk11-openjdk-slim

# 既存のROOTアプリケーションを削除
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# プロジェクトファイルを直接コピーする方法
COPY WebContent/ /usr/local/tomcat/webapps/ROOT/
COPY src/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# または、WARファイルが存在する場合はそれを使用
# COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

ENV CATALINA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8080
CMD ["catalina.sh", "run"]