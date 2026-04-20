-- 1. ユーザー情報のテーブル
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- 2. 権限（ロール）のテーブル
CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

-- 3. タレント（掲示板の投稿）テーブル
CREATE TABLE IF NOT EXISTS talent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    talent_name VARCHAR(100) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
);