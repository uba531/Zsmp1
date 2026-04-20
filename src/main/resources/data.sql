-- 一旦データを消去
DELETE FROM authorities;
DELETE FROM users;

-- ユーザーの登録
INSERT INTO users (username, password, enabled) VALUES 
('admin', '$2a$10$GW.cAVRMkV6HPCrph/sT.ehfjQRTYgXTUq3letl3Et60XnTKqtxwu', true),
('user', '$2a$10$0Ai8aFAJht8OHiJQj5Ywe.tW3VFJxxrbqzNmSXSJ5LfGA3ATYPVmK', true);

-- 権限の割当
INSERT INTO authorities (username, authority) VALUES 
('admin', 'ROLE_ADMIN'),
('user', 'ROLE_USER');