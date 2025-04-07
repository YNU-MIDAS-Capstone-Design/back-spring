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


INSERT INTO users (user_id, nickname, email)
VALUES
    (1, 'alice', 'alice@example.com'),
    (2, 'bob', 'bob@example.com'),
    (3, 'charlie', 'charlie@example.com');

INSERT INTO project (title, description, processing, start_date, project_period, people, content, meet_location, project_like, project_view, created_at, user_id)
VALUES
-- 1. 최신 + 보통 조회수
('AI 쇼핑몰 프로젝트', 'AI 추천 알고리즘을 기반으로 한 스마트 쇼핑몰 웹 프로젝트의 팀원을 모집합니다.', '모집중', CURRENT_TIMESTAMP, 30, 3, 'Python과 Django, 협업툴로 Notion 사용.', '경기도', 12, 150, CURRENT_TIMESTAMP, 1),

-- 2. 최신에서 두 번째 + 조회수 가장 낮음
('Spring Boot API 서버 개발', 'Spring Boot를 활용한 쇼핑몰 백엔드 API 서버를 개발할 팀원을 찾습니다.', '모집중', CURRENT_TIMESTAMP, 45, 4, 'JWT, JPA, MySQL 기반.', '경상남도', 8, 40, DATE_SUB(NOW(), INTERVAL 1 DAY), 2),

-- 3. 3일 전 + 조회수 보통
('React UI/UX 개선', 'React 기반의 UI/UX 개선 프로젝트를 함께할 프론트엔드 개발자를 모집합니다.', '모집중', CURRENT_TIMESTAMP, 20, 2, 'TypeScript, Figma 디자인 시스템 사용 예정.', '경기도', 5, 110, DATE_SUB(NOW(), INTERVAL 2 DAY), 3),

-- 4. 4일 전 + 조회수 상위
('영화 추천 시스템 개발', '머신러닝 기반 개인화 영화 추천 웹 애플리케이션을 만들 팀원을 찾습니다.', '모집중', CURRENT_TIMESTAMP, 35, 3, 'Pandas, Scikit-learn, Flask 사용.', '경상북도', 15, 210, DATE_SUB(NOW(), INTERVAL 3 DAY), 1),

-- 5. 5일 전 + 조회수 하위
('실시간 채팅 웹앱', 'WebSocket 기반 실시간 채팅 웹앱 프로젝트에 참여할 인원을 모집합니다.', '모집중', CURRENT_TIMESTAMP, 25, 3, 'Spring WebSocket, STOMP, Redis 활용.', '경기도', 10, 60, DATE_SUB(NOW(), INTERVAL 4 DAY), 2),

-- 6. 6일 전 + **가장 인기**
('게임 커뮤니티 플랫폼', '게임 유저를 위한 커뮤니티 플랫폼 웹 프로젝트 팀원을 모집합니다.', '모집중', CURRENT_TIMESTAMP, 40, 5, 'React, Spring Boot, MongoDB 사용.', '경상남도', 22, 300, DATE_SUB(NOW(), INTERVAL 5 DAY), 3),

-- 7. 7일 전 + 조회수 하위
('개인 포트폴리오 제작', 'Next.js 기반 포트폴리오 웹사이트 제작 프로젝트입니다. 프론트엔드 팀원을 찾습니다.', '모집완료', CURRENT_TIMESTAMP, 10, 1, 'Next.js, TailwindCSS, Vercel 배포.', '경기도', 3, 50, DATE_SUB(NOW(), INTERVAL 4 DAY), 1),

-- 8. 8일 전 + 조회수 중간
('데이터 시각화 대시보드', '차트와 그래프를 포함한 웹 기반 데이터 대시보드 개발자를 모집합니다.', '수정중', CURRENT_TIMESTAMP, 30, 3, 'Chart.js, Spring Boot, PostgreSQL 사용.', '경상북도', 6, 130, DATE_SUB(NOW(), INTERVAL 5 DAY), 2),

-- 9. 9일 전 + 인기 상위
('헬스케어 웹 플랫폼', '운동 기록 관리 및 분석 웹 플랫폼 프로젝트입니다. 헬스케어에 관심 있는 분을 찾습니다.', '모집완료', CURRENT_TIMESTAMP, 50, 4, 'React Native, Node.js, MySQL 활용.', '경상남도', 18, 240, DATE_SUB(NOW(), INTERVAL 6 DAY), 3),

-- 10. 10일 전 + 인기 중상
('여행 커뮤니티 웹 서비스', '여행 후기 공유 및 동행 매칭 서비스 개발 프로젝트입니다.', '모집완료', CURRENT_TIMESTAMP, 28, 3, 'Spring Boot, React, AWS 배포.', '경기도', 20, 200, DATE_SUB(NOW(), INTERVAL 7 DAY), 1);

INSERT INTO project_stack (project_id, stack) VALUES
-- 1. AI 쇼핑몰 프로젝트
(1, 'Python'),
(1, 'Django'),
(1, 'AI'),

-- 2. Spring Boot API 서버 개발
(2, 'Java'),
(2, 'Spring'),
(2, 'MySQL'),

-- 3. React UI/UX 개선
(3, 'React'),

-- 4. 영화 추천 시스템 개발
(4, 'Python'),
(4, 'Flask'),
(4, 'AI'),
(4, 'BigData'),

-- 5. 실시간 채팅 웹앱
(5, 'Spring'),

-- 6. 게임 커뮤니티 플랫폼
(6, 'React'),
(6, 'Spring'),
(6, 'MongoDB'),

-- 7. 개인 포트폴리오 제작
(7, 'React'),  -- Next.js 대신 대체

-- 8. 데이터 시각화 대시보드
(8, 'Spring'),
(8, 'BigData'),

-- 9. 헬스케어 웹 플랫폼
(9, 'React'),
(9, 'Nodejs'),
(9, 'MySQL'),

-- 10. 여행 커뮤니티 웹 서비스
(10, 'React'),
(10, 'Spring'),
(10, 'AWS');