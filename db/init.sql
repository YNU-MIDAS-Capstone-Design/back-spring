CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio VARCHAR(255),
    location VARCHAR(255),
    sns VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_tech_stacks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS project (
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    content TEXT,
    processing VARCHAR(255),
    recruitment_field VARCHAR(255),
    people INT DEFAULT 0,
    meet_location VARCHAR(255),
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS project_stack (
    project_stack_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stack VARCHAR(255),
    project_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE IF NOT EXISTS project_like (
    like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS project_comment(
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    project_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS team (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS team_member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_name VARCHAR(255),
    owner BOOLEAN,
    team_role VARCHAR(255),
    team_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (team_id) REFERENCES team(team_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE IF NOT EXISTS team_calendar (
    cal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cal_date TIMESTAMP,
    content VARCHAR(255),
    team_id BIGINT,
    FOREIGN KEY (team_id) REFERENCES team(team_id)
);


INSERT INTO users (user_id, nickname, email, password, bio, location, sns)
VALUES
    (1, 'eunseo', 'eunseo@naver.com', '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
     '백엔드 개발자 지망생입니다. Spring Boot에 관심이 많아요.', '경상북도', 'https://github.com/devlover'),
    (2, 'bob', 'bob@naver.com', '$2a$10$.QiPgFUltC2cWZIfGliCk.vj0Zwtux8am00R21sAJrYIzIyFAU7By',
     '백엔드 개발자 지망생입니다. Spring Boot에 관심이 많아요.', '경상남도', 'https://github.com/devlover'),
    (3, 'charlie', 'charlie@naver.com', '$2a$10$EweauHtFZh7umLbxS/Z9p.FuelbCXoZwiu6lVqobYnCKBiPHgoqAe',
     '백엔드 개발자 지망생입니다. Spring Boot에 관심이 많아요.', '강원도', 'https://github.com/devlover');
-- 비밀번호 : eunseoPassword123!, bobPassword123!, charliePassword123!

INSERT INTO team (team_id, team_name)
VALUES
    (1, 'AI 프로젝트 팀'),
    (2, '웹 개발 팀');

INSERT INTO team_member (member_id, member_name, owner, team_role, team_id, user_id)
VALUES
    (1, 'eunseo', true, '프론트', 1, 1),
    (3, 'bob', false, '프론트', 1, 2),
    (4, 'charlie', false, '백엔드', 1, 3),
    (2, 'eunseo', true, '프론트', 2, 1),
    (5, 'bob', false, '디자이너', 2, 2);

INSERT INTO team_calendar (cal_id, cal_date, content, team_id)
VALUES
    (1, '2025-04-10 00:00:00', 'AI 모델 설계 회의', 1),
    (2, '2025-04-12 00:00:00', '데이터 수집 마감일', 1),
    (3, '2025-04-08 00:00:00', '웹 프론트 UI 회의', 2),
    (4, '2025-04-11 00:00:00', '백엔드 API 리뷰', 2);

