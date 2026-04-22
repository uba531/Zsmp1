# 掲示板アプリ（Zsmp1）

## 概要
ログイン機能付きの掲示板アプリです。
投稿の作成・編集・削除ができます。

## 使用技術
- Java 21
- Spring Boot
- Spring Security
- Thymeleaf
- H2 Database

## 機能
- ログイン / ログアウト
- 投稿の作成・一覧表示
- 投稿の編集・削除
- キーワード検索
- ページング

## 工夫した点
- 日時はUTCで保存し、表示時に東京時間へ変換
- ControllerとServiceを分離して設計
- Spring Securityで認証機能を実装