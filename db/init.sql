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
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    profile_image_filename VARCHAR(255)
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

INSERT INTO user_tech_stacks (name, user_id)
VALUES
-- eunseo (user_id = 1)
('Spring', 1),
('Java', 1),
('MySQL', 1),
-- bob (user_id = 2)
('React', 2),
('JavaScript', 2),
('HTML', 2),
-- charlie (user_id = 3)
('Python', 3),
('Django', 3),
('Docker', 3);


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


INSERT INTO project (project_id, title, description, content, processing, recruitment_field, people, meet_location, like_count, view_count, created_at, user_id)
VALUES
(1, '모바일 개발자 모집 프로젝트 1', '경상북도 지역에서 함께할 모바일 포지션 팀원 모집', '모바일 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집중', '모바일', 2, '경상북도', 0, 486, '2025-04-10 10:23:22', 2),
(2, '프론트 개발자 모집 프로젝트 2', '경상남도 지역에서 함께할 프론트 포지션 팀원 모집', '프론트 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '수정중', '프론트', 4, '경상남도', 42, 458, '2025-04-20 10:23:22', 2),
(3, '인공지능 개발자 모집 프로젝트 3', '충청남도 지역에서 함께할 인공지능 포지션 팀원 모집', '인공지능 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집중', '인공지능', 5, '충청남도', 25, 283, '2025-05-01 10:23:22', 1),
(4, '백엔드 개발자 모집 프로젝트 4', '경상남도 지역에서 함께할 백엔드 포지션 팀원 모집', '백엔드 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집중', '백엔드', 3, '경상남도', 31, 292, '2025-04-09 10:23:22', 2),
(5, '백엔드 개발자 모집 프로젝트 5', '충청북도 지역에서 함께할 백엔드 포지션 팀원 모집', '백엔드 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '수정중', '백엔드', 6, '충청북도', 28, 369, '2025-04-03 10:23:22', 1),
(6, '디자이너 개발자 모집 프로젝트 6', '전라남도 지역에서 함께할 디자이너 포지션 팀원 모집', '디자이너 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '수정중', '디자이너', 6, '전라남도', 12, 404, '2025-04-17 10:23:22', 3),
(7, '디자이너 개발자 모집 프로젝트 7', '충청북도 지역에서 함께할 디자이너 포지션 팀원 모집', '디자이너 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집완료', '디자이너', 6, '충청북도', 4, 237, '2025-04-24 10:23:22', 2),
(8, '디자이너 개발자 모집 프로젝트 8', '경상남도 지역에서 함께할 디자이너 포지션 팀원 모집', '디자이너 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집완료', '디자이너', 5, '경상남도', 33, 132, '2025-04-13 10:23:22', 3),
(9, '모바일 개발자 모집 프로젝트 9', '전라북도 지역에서 함께할 모바일 포지션 팀원 모집', '모바일 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '수정중', '모바일', 4, '전라북도', 10, 172, '2025-04-16 10:23:22', 3),
(10, '프론트 개발자 모집 프로젝트 10', '충청북도 지역에서 함께할 프론트 포지션 팀원 모집', '프론트 관련 기술을 활용한 실전 프로젝트를 함께 진행합니다.', '모집완료', '프론트', 6, '충청북도', 13, 282, '2025-04-13 10:23:22', 3);


INSERT INTO project_stack (project_id, stack) VALUES
(1, 'security'),
(1, 'Adobe_XD'),
(1, 'NestJS'),
(1, 'MySQL'),
(2, 'Vue'),
(2, 'HTML'),
(2, 'MySQL'),
(3, 'NestJS'),
(3, 'MySQL'),
(3, 'Illustrator'),
(3, 'Java'),
(4, 'NestJS'),
(4, 'MongoDB'),
(4, 'security'),
(5, 'React'),
(5, 'Docker'),
(5, 'security'),
(5, 'Illustrator'),
(6, 'IoT'),
(6, 'Java'),
(6, 'C_PLUS'),
(7, 'Docker'),
(7, 'security'),
(8, 'NestJS'),
(8, 'Spring'),
(8, 'HTML'),
(9, 'security'),
(9, 'Java'),
(10, 'security');
