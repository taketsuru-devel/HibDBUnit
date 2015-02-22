# HibDBUnit
Hibernateの設定ファイルを使ってDBUnitによるテストをしたい＆データベースのバックアップをするDBUnitの実装です。<br>
A Package that DBUnit extends with using Hibernate configuration file and backup database.<br>

# Overview
このパッケージをjavaプロジェクトにインポートし、HibDBUnit.classを継承したテストクラスを作成します。<br>
コンストラクタにおいてHibernateの設定ファイル、テストデータの設定ファイルなどを設定します。<br>
* '必須:'使用するHibernateの設定ファイル
* '必須:'使用するテストデータの定義ファイル(複数定義可能)
* '任意:'使用するデータベースの定義クラス(省略した場合はMySQL)
* '任意:'使用するテストデータの読み取りクラス(省略した場合はDBUnitで認識できるxml,csv形式に対応)

あとはテストメソッドを定義し、JUnit testを実行してください。<br>


Import this package for your java project, then create test class extends HibDBUnit.class .<br>
Define Hibernte configuration file, test data file and others in constructer.<br>
* 'must:' Hibernate configuration file
* 'must:' test data file(s)
* 'additional:' database definition class (default: MySQL)
* 'additional:' test data parser class (default: xml,csv DBUnit) 

Then create test method and run JUnit test.<br>

# Dependencies
このパッケージは以下のパッケージを必要とします。<br>
mavenのgroupid、versionを記載しています。<br>
* org.dbUnit 2.5.0
* org.hibernate 4.3.7.Final
* org.slf4j 1.7.10
* ch.qos.logback 1.1.2
* commons-logging 1.2
* mysql 5.1.34

commons-logging、mysql-connecter-javaはテストクラスを分離して削除する予定です。<br>

This package depends on these packages.<br>
Descriptions are groupid and version of maven.<br>
* org.dbUnit 2.5.0
* org.hibernate 4.3.7.Final
* org.slf4j 1.7.10
* ch.qos.logback 1.1.2
* commons-logging 1.2
* mysql 5.1.34

I will delete commons-logging and mysql-connecter-java by separating test class.

