# root password 6212
CREATE DATABASE IF NOT EXISTS portfolio_db;
CREATE USER 'appuser'@'%' IDENTIFIED BY 'appuser';
GRANT ALL PRIVILEGES ON portfolio_db.* TO 'appuser'@'%';
FLUSH PRIVILEGES;

create table if not exists portfolio_db.role (
                      id int primary key auto_increment comment 'primary key',
                      role_code varchar(10) unique comment '권한 코드',
                      role_name varchar(20) comment '권한명',
                      created_at timestamp default current_timestamp comment '등록일',
                      updated_at timestamp default current_timestamp on update current_timestamp comment '수정일'
);


create table if not exists portfolio_db.users (
                       id int primary key auto_increment comment 'primary key',
                       login_id varchar(20) unique comment '로그인 id',
                       login_pw varchar(255) comment '로그인 pw',
                       user_name varchar(20) comment '사용자명',
                       created_at timestamp default current_timestamp comment '가입일'
);

create table if not exists portfolio_db.user_role (
                           user_id int not null comment 'users 테이블 참조 외래 키',
                           role_id int not null comment 'role 테이블 참조 외래 키',
                           primary key (user_id, role_id),
                           foreign key (user_id) references users(id),
                           foreign key (role_id) references role(id)
) comment 'users와 role 사이의 다대다 테이블';


create table if not exists portfolio_db.user_login_history
(
    id          bigint auto_increment
        primary key,
    loginId     varchar(20)                      not null,
    action_type  varchar(10)                         not null,
    ip_address  varchar(255)                        null,
    device_info varchar(255)                        null,
    action_time  timestamp default CURRENT_TIMESTAMP not null
);


create table portfolio_db.menu
(
    id               int auto_increment comment 'primary key'
        primary key,
    upper_id         int                                 null comment '상위 menu id',
    menu_name        varchar(20)                         not null comment '로그인 id',
    created_at       timestamp default CURRENT_TIMESTAMP not null comment '등록일',
    updated_at       timestamp default CURRENT_TIMESTAMP not null comment '수정일',
    LAST_MODIFIED_BY varchar(20)                         not null comment '마지막 수정자 ID',
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

create index upper_id
    on menu (upper_id);

-- auto-generated definition
create table program
(
    id              int auto_increment comment '프로그램 기본 키'
        primary key,
    program_name    varchar(20)                         not null comment '프로그램 명',
    url             varchar(255)                        not null comment '프로그램 URL',
    created_at      timestamp default CURRENT_TIMESTAMP not null comment '생성일',
    updated_at      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정일',
    last_updated_by int                                 null comment '마지막으로 수정한 사용자의 ID',
    role_id         int                                 null comment '권한 ID',
    constraint url_unique
        unique (url) comment 'URL은 고유해야 합니다.',
    constraint fk_program_role
        foreign key (role_id) references role (id)
            on update cascade on delete set null
);

#
# insert into portfolio_db.role (role_code, role_name)
# values ('ROLE_ADMIN', '관리자');
# insert into portfolio_db.role (role_code, role_name)
# values ('ROLE_USER', '사용자');


commit;
