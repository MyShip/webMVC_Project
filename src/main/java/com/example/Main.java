package com.example;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        // ポート番号の取得（Renderから環境変数で渡される）
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Tomcat tomcat = new Tomcat();
        
        // ポート設定
        tomcat.setPort(Integer.valueOf(webPort));
        
        // コネクタを取得（必要な初期化）
        tomcat.getConnector();

        // Webアプリケーションのコンテキストを追加
        Context context = tomcat.addWebapp("", getWebappDirLocation());
        
        // クラスパスからリソースを読み込む設定
        String additionWebInfClassesPath = Main.class.getProtectionDomain()
                .getCodeSource().getLocation().toString();
        
        // リソースの設定
        StandardRoot root = new StandardRoot(context);
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes",
                additionWebInfClassesPath, "/"));
        context.setResources(root);

        System.out.println("Starting Tomcat on port: " + webPort);
        System.out.println("Webapp directory: " + getWebappDirLocation());
        
        tomcat.start();
        tomcat.getServer().await();
    }

    private static String getWebappDirLocation() {
        // JARファイル内のリソースパスを取得
        String resourcePath = Main.class.getClassLoader().getResource("META-INF/resources").toString();
        
        // ローカル開発時の処理
        if (resourcePath.startsWith("file:")) {
            return resourcePath.substring(5); // "file:"を除去
        }
        
        // JARファイル内の場合
        if (resourcePath.startsWith("jar:file:")) {
            // JARファイルからリソースを展開する処理
            return extractWebappFromJar();
        }
        
        // フォールバック: 現在のディレクトリ
        return new File("src/main/webapp").getAbsolutePath();
    }
    
    private static String extractWebappFromJar() {
        // JARファイルからWebアプリケーションリソースを一時ディレクトリに展開
        try {
            File tempDir = File.createTempFile("webapp", "");
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            
            // リソースをコピー（実装は省略、必要に応じて追加）
            return tempDir.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return new File(".").getAbsolutePath();
        }
    }
}