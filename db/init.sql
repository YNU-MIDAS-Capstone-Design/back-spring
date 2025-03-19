CREATE DATABASE IF NOT EXISTS mydb;

USE mydb;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    location VARCHAR(255),
    sns VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS project (
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    processing VARCHAR(255),
    start_date DATETIME DEFAULT NULL,
    project_period INT DEFAULT 0,
    people INT DEFAULT 0,
    content TEXT,
    meet_location VARCHAR(255),
    project_like INT DEFAULT 0,
    project_view INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS project_stack (
    project_stack_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    stack VARCHAR(255),
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE IF NOT EXISTS project_like (
    like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);