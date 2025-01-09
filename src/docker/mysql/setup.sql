# root password 6212
CREATE DATABASE IF NOT EXISTS portfolio_db;
CREATE USER 'appuser'@'%' IDENTIFIED BY 'appuser';
GRANT ALL PRIVILEGES ON portfolio_db.* TO 'appuser'@'%';
FLUSH PRIVILEGES;
# 초기화
drop table menu_role;
drop table menu;
drop table program;
drop table user_role;
drop table `role`;
drop table users;
drop table user_login_history;
#
#
#
create table if not exists portfolio_db.role
(
    id         int(11)   NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    upper_id   int(11)        DEFAULT NULL COMMENT '상위 권한 ID',
    role_code  varchar(10)    DEFAULT NULL COMMENT '권한 코드',
    role_name  varchar(20)    DEFAULT NULL COMMENT '권한명',
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (id),
    UNIQUE KEY role_code (role_code),
    KEY upper_id (upper_id),
    CONSTRAINT role_ibfk_1 FOREIGN KEY (upper_id) REFERENCES role (id) ON DELETE SET NULL ON UPDATE CASCADE
);


create table if not exists portfolio_db.users
(
    id         int primary key auto_increment comment 'primary key',
    login_id   varchar(20) unique comment '로그인 id',
    login_pw   varchar(255) comment '로그인 pw',
    user_name  varchar(20) comment '사용자명',
    created_at timestamp default current_timestamp comment '가입일'
);

create table if not exists portfolio_db.user_role
(
    user_id int not null comment 'users 테이블 참조 외래 키',
    role_id int not null comment 'role 테이블 참조 외래 키',
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references role (id)
) comment 'users와 role 사이의 다대다 테이블';



create table if not exists portfolio_db.user_login_history
(
    id          bigint auto_increment primary key,
    login_id    varchar(20)                         not null,
    action_type varchar(10)                         not null,
    ip_address  varchar(255)                        null,
    device_info varchar(255)                        null,
    action_time timestamp default CURRENT_TIMESTAMP not null
);


-- auto-generated definition
create table program
(
    id              int auto_increment comment '프로그램 기본 키'
        primary key,
    program_name    varchar(20)                          not null comment '프로그램 명',
    url             varchar(255)                         not null comment '프로그램 URL',
    created_at      timestamp  default CURRENT_TIMESTAMP not null comment '생성일',
    updated_at      timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정일',
    last_updated_by int                                  null comment '마지막으로 수정한 사용자의 USER ID',
    is_immutable    varchar(2) DEFAULT 'N',
    constraint url_unique
        unique (url) comment 'URL은 고유해야 합니다.'
);

create table portfolio_db.menu
(
    id               int auto_increment comment 'primary key'
        primary key,
    upper_id         int                                 null comment '상위 menu id',
    menu_name        varchar(20)                         not null comment '로그인 id',
    created_at       timestamp default CURRENT_TIMESTAMP not null comment '등록일',
    updated_at       timestamp default CURRENT_TIMESTAMP not null comment '수정일',
    last_modified_by int                                 not null comment '마지막 수정자 USER ID',
    order_num        int       default 0                 not null comment '메뉴 순서',
    menu_type        varchar(10)                         not null comment '메뉴 타입',
    program_id       int                                 null comment '프로그램 ID',
    constraint fk_menu_program
        foreign key (program_id) references program (id)
            on update cascade on delete set null,
    constraint menu_ibfk_1
        foreign key (upper_id) references menu (id)
            on update cascade on delete set null
);


create table if not exists portfolio_db.menu_role
(
    menu_id    int NOT NULL,
    role_id    int NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (menu_id, role_id),
    KEY fk_menu_role_menu (menu_id),
    KEY fk_menu_role_role (role_id),
    CONSTRAINT fk_menu_role_menu FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_menu_role_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE
) comment 'menu와 role 사이의 다대다 테이블';

create index upper_id
    on menu (upper_id);

-- schedule_category 테이블 생성
create table if not exists portfolio_db.schedule_category
(
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT 'primary key',                            -- 기본 키
    user_id     INT          NOT NULL COMMENT 'users 테이블의 id를 참조',                               -- 외래 키
    name        VARCHAR(255) NOT NULL COMMENT '카테고리 이름',                                         -- 카테고리 이름
    description TEXT COMMENT '카테고리 설명',                                                          -- 카테고리 설명
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',                             -- 생성 시간
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간', -- 수정 시간
    FOREIGN KEY (user_id) REFERENCES portfolio_db.users (id) ON DELETE CASCADE                   -- users 테이블과의 관계 설정
);

-- schedule_task 테이블 생성
create table if not exists portfolio_db.schedule_task
(
    id                   INT AUTO_INCREMENT PRIMARY KEY, -- 기본 키
    schedule_category_id INT          NOT NULL,  -- 외래 키
    name                 VARCHAR(255) NOT NULL,  -- 작업 이름
    description          TEXT,                   -- 작업 설명
    start_date           DATE         NOT NULL,  -- 작업 시작 날짜
    end_date             DATE         NOT NULL,  -- 작업 종료 날짜
    status               ENUM ('ACTIVE', 'DONE', 'CRITICAL') DEFAULT 'ACTIVE',          -- 작업 상태
    created_at           TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
    updated_at           TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시간
    FOREIGN KEY (schedule_category_id) REFERENCES portfolio_db.schedule_category (id) ON DELETE CASCADE      -- 외래 키 제약 조건
);


insert into portfolio_db.role (role_code, role_name)
values ('ROLE_ADMIN', '관리자');
insert into portfolio_db.role (role_code, role_name)
values ('ROLE_USER', '사용자');
insert into portfolio_db.role (role_code, role_name)
values ('ROLE_GUEST', 'GUEST');

insert into users(
    login_id, login_pw, user_name
)
values (
           'admin',
           '$2a$10$17P3JO3uyn31EejBBPwFfue4BEWDHHHfQQvFvn4Z.35Hkvw2nLAJi',
           'admin'
       );
insert into user_role(user_id, role_id)
values (
           1, 1
       );

insert into program(
    program_name, url, last_updated_by, is_immutable
)
values (
           '프로그램 관리', '/admin/programManage', 1, 'Y'
       );

insert into program(
    program_name, url, last_updated_by, is_immutable
)
values (
           '메뉴 관리', '/admin/menuManage', 1, 'Y'
       );

insert into program(
    program_name, url, last_updated_by, is_immutable
)
values (
           '권한 관리', '/admin/roleManage', 1, 'Y'
       );

insert into program(
    program_name, url, last_updated_by, is_immutable
)
values (
           '사용자 관리', '/admin/userManage', 1, 'Y'
       );

insert into menu (
    menu_name, order_num, menu_type, last_modified_by
)
values (
           '환경설정', 999, 'FOLDER', 1
       );

insert into menu (upper_id, menu_name, last_modified_by, order_num, menu_type, program_id)
values (
           1, '프로그램관리', 1, 1, 'PROGRAM', 1
       );

insert into menu (upper_id, menu_name, last_modified_by, order_num, menu_type, program_id)
values (
           1, '메뉴관리', 1, 2, 'PROGRAM', 2
       );

insert into menu (upper_id, menu_name, last_modified_by, order_num, menu_type, program_id)
values (
           1, '권한관리', 1, 3, 'PROGRAM', 3
       );

insert into menu (upper_id, menu_name, last_modified_by, order_num, menu_type, program_id)
values (
           1, '사용자관리', 1, 4, 'PROGRAM', 4
       );

insert into menu_role (menu_id, role_id)
values (1, 1);
insert into menu_role (menu_id, role_id)
values (2, 1);
insert into menu_role (menu_id, role_id)
values (3, 1);
insert into menu_role (menu_id, role_id)
values (4, 1);

commit;
