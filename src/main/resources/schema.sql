-- ユーザー情報を保存するテーブル
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY, -- ユーザー名（重複不可）
    password VARCHAR(100) NOT NULL,            -- ハッシュ化されたパスワード
    enabled BOOLEAN NOT NULL                   -- アカウントが有効かどうか
);

-- 権限（ロール）を保存するテーブル
CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);