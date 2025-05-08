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
    profile_image_url VARCHAR(255)
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
CREATE TABLE IF NOT EXISTS project_applicant (
    applicant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accepted BOOLEAN DEFAULT FALSE,
    project_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id),
    FOREIGN KEY (user_id)    REFERENCES users(user_id)
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



INSERT INTO users (user_id, nickname, email, password, bio, location, sns) VALUES
                                                                               (4,  'david',  'david@naver.com',  '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '경기도',       'https://github.com/david'),
                                                                               (5,  'emma',   'emma@naver.com',   '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '충청북도',     'https://github.com/emma'),
                                                                               (6,  'frank',  'frank@naver.com',  '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '충청남도',     'https://github.com/frank'),
                                                                               (7,  'grace',  'grace@naver.com',  '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '전라북도',     'https://github.com/grace'),
                                                                               (8,  'henry',  'henry@naver.com',  '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '전라남도',     'https://github.com/henry'),
                                                                               (9,  'irene',  'irene@naver.com',  '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '경상북도',     'https://github.com/irene'),
                                                                               (10, 'jack',   'jack@naver.com',   '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '경상남도',     'https://github.com/jack'),
                                                                               (11, 'kate',   'kate@naver.com',   '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '강원도',       'https://github.com/kate'),
                                                                               (12, 'leo',    'leo@naver.com',    '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '경기도',       'https://github.com/leo'),
                                                                               (13, 'mia',    'mia@naver.com',    '$2a$10$t9/b6NAzhtuKc.BibC3wzuRtUh/WK/0kx8xfEAqMvSZHq.wiPGMVq',
                                                                                '테스트 유저입니다.',               '충청북도',     'https://github.com/mia');


INSERT INTO user_tech_stacks (name, user_id) VALUES
                                                 ('Spring', 1),
                                                 ('JavaScript', 1),
                                                 ('JavaScript', 1),

                                                 ('JavaScript', 1),

                                                 ('JavaScript', 12),

                                                 ('React', 12);


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

INSERT INTO project (title,description,content,processing,recruitment_field,people,meet_location,like_count,view_count,created_at,user_id)
VALUES ('Machine Learning Pipeline','ML 모델 학습 파이프라인','데이터 전처리→모델 배포','모집중','개발',5,'경상북도',0,0,'2025-05-03 10:00:00',2);
INSERT INTO project (title,description,content,processing,recruitment_field,people,meet_location,like_count,view_count,created_at,user_id)
VALUES ('Docker CI/CD Setup','CI/CD 파이프라인 구축','GitHub Actions + Docker Registry','모집중','운영',2,'경상북도',0,0,'2025-05-04 11:30:00',3);
INSERT INTO project (title,description,content,processing,recruitment_field,people,meet_location,like_count,view_count,created_at,user_id) VALUES ('React Native Startup','모바일 앱 개발','로그인·네비게이션·푸시 알림','모집중','개발',4,'충청북도',0,0,'2025-05-05 14:00:00',1);
INSERT INTO project (project_id, title, description, content, processing, recruitment_field, people, meet_location, like_count, view_count, created_at, user_id) VALUES
                                                                                                                                                                     (4,  'Node.js REST API',        'Express 기반 REST API 서버 개발',         'CRUD 엔드포인트, JWT 인증',        '모집중', '개발', 4, '경기도',    0, 0, '2025-05-06 09:00:00', 4),
                                                                                                                                                                     (5,  'Data Analytics Pipeline',  '데이터 수집 및 분석 파이프라인 구축',      'Pandas, Spark 연동',               '모집중', '개발', 5, '강원도',    0, 0, '2025-05-06 10:30:00', 5),
                                                                                                                                                                     (6,  'Spring Boot E-Commerce',  '전자상거래 플랫폼 백엔드 개발',           '주문·결제·상품관리 API',            '모집중', '개발', 6, '충청북도',  0, 0, '2025-05-07 11:00:00', 6),
                                                                                                                                                                     (7, 'React Frontend Refactor', '기존 UI를 React로 리팩토링',             'Redux, Hooks 사용',                '모집중', '개발', 3, '충청남도',  0, 0, '2025-05-07 14:15:00', 7),
                                                                                                                                                                     (8, 'Dockerized Microservices','마이크로서비스 도커라이징',              'Docker Compose 및 Swarm 구성',      '모집중', '운영', 4, '전라북도',  0, 0, '2025-05-08 09:45:00', 8),
                                                                                                                                                                     (9, 'AI Chatbot Integration',  '챗봇 API 통합 및 웹 인터페이스',        'Flask, TensorFlow 사용',           '모집중', '개발', 3, '전라남도',  0, 0, '2025-05-08 13:20:00', 9),
                                                                                                                                                                     (10, 'Big Data ETL Tool',       '대규모 데이터 ETL 워크플로우 개발',      'Kafka, Hadoop 연동',               '모집중', '개발', 5, '경상북도',  0, 0, '2025-05-09 10:00:00',10),
                                                                                                                                                                     (11, 'Cloud Infra Setup',       '클라우드 인프라 IaC 구성',              'Terraform, AWS 모듈 사용',        '모집중', '운영', 4, '경상남도',  0, 0, '2025-05-09 15:30:00',11);

INSERT INTO project_stack (stack,project_id) VALUES ('Python',1),('AI',1),('BigData',1);
INSERT INTO project_stack (stack,project_id) VALUES ('Docker',2),('AWS',2);
INSERT INTO project_stack (stack,project_id) VALUES ('React',3),('JavaScript',3);
INSERT INTO project_stack (stack, project_id) VALUES
                                                  -- 프로젝트 1
                                                  ('Python',    1),
                                                  ('AI',        1),
                                                  ('BigData',   1),
                                                  -- 프로젝트 2
                                                  ('Docker',    2),
                                                  ('AWS',       2),
                                                  -- 프로젝트 3
                                                  ('React',     3),
                                                  ('JavaScript',3),
                                                  -- 추가로 만드신 프로젝트 4,5,6에도 enum 에 맞게 스택만 넣으세요
                                                  -- 예시)
                                                  ('Python',    4),
                                                  ('AI',        4),
                                                  ('Docker',    5),
                                                  ('AWS',       5),
                                                  ('React',     6),
                                                  ('JavaScript',6);

INSERT INTO project_like (project_id, user_id) VALUES
                                                   (1, 2),
                                                   (1, 3),
                                                   (1, 4),
                                                   (2, 1),
                                                   (2, 3),
                                                   (3, 1),
                                                   (3, 2),
                                                   (4, 5),
                                                   (4, 6),
                                                   (5, 7),
                                                   (5, 8),
                                                   (6, 9),
                                                   (6, 10),
                                                   (7, 11),
                                                   (7, 12),
                                                   (8, 13),
                                                   (8, 1),
                                                   (9, 2),
                                                   (9, 3),
                                                   (10, 4),
                                                   (10, 5),
                                                   (11, 6),
                                                   (11, 7);


INSERT INTO project_applicant (accepted, project_id, user_id) VALUES
                                                                  (FALSE, 1, 5),
                                                                  (TRUE,  1, 6),
                                                                  (FALSE, 2, 7),
                                                                  (TRUE,  2, 8),
                                                                  (FALSE, 3, 9),
                                                                  (TRUE,  3, 10),
                                                                  (FALSE, 4, 11),
                                                                  (TRUE,  4, 12),
                                                                  (FALSE, 5, 13),
                                                                  (TRUE,  5, 1),
                                                                  (FALSE, 6, 2),
                                                                  (TRUE,  6, 3),
                                                                  (FALSE, 7, 4),
                                                                  (TRUE,  7, 5),
                                                                  (FALSE, 8, 6),
                                                                  (TRUE,  8, 7),
                                                                  (FALSE, 9, 8),
                                                                  (TRUE,  9, 9),
                                                                  (FALSE, 10, 10),
                                                                  (TRUE,  10, 11),
                                                                  (FALSE, 11, 12),
                                                                  (TRUE,  11, 13);


INSERT INTO project_applicant (accepted, project_id, user_id) VALUES
                                                                  (FALSE, 1, 5),
                                                                  (TRUE,  1, 6),
                                                                  (FALSE, 2, 7),
                                                                  (TRUE,  2, 8),
                                                                  (FALSE, 3, 9),
                                                                  (TRUE,  3, 10),
                                                                  (FALSE, 4, 11),
                                                                  (TRUE,  4, 12),
                                                                  (FALSE, 5, 13),
                                                                  (TRUE,  5, 1),
                                                                  (FALSE, 6, 2),
                                                                  (TRUE,  6, 3),
                                                                  (FALSE, 7, 4),
                                                                  (TRUE,  7, 5),
                                                                  (FALSE, 8, 6),
                                                                  (TRUE,  8, 7),
                                                                  (FALSE, 9, 8),
                                                                  (TRUE,  9, 9),
                                                                  (FALSE, 10, 10),
                                                                  (TRUE,  10, 11),
                                                                  (FALSE, 11, 12),
                                                                  (TRUE,  11, 13);