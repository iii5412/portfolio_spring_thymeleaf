drop table user_role;
drop table role;
drop table users;


create table role (
                      id int primary key auto_increment comment 'primary key',
                      role_code varchar(10) unique comment '권한 코드',
                      role_name varchar(20) comment '권한명',
                      created_at timestamp default current_timestamp comment '등록일',
                      updated_at timestamp default current_timestamp on update current_timestamp comment '수정일'
);


create table users (
                       id int primary key auto_increment comment 'primary key',
                       login_id varchar(20) unique comment '로그인 id',
                       login_pw varchar(20) comment '로그인 pw',
                       user_name varchar(20) comment '사용자명',
                       created_at timestamp default current_timestamp comment '가입일'
);

create table user_role (
                           user_id int not null comment 'users 테이블 참조 외래 키',
                           role_id int not null comment 'role 테이블 참조 외래 키',
                           primary key (user_id, role_id),
                           foreign key (user_id) references users(id),
                           foreign key (role_id) references role(id)
) comment 'users와 role 사이의 다대다 테이블'


CREATE TABLE user_login_history (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id INT NOT NULL, -- 외래키 (사용자 테이블과 연결)
                                    actionType VARCHAR(10) NOT NULL, -- LOGIN, LOGOUT
                                    ip_address VARCHAR(255), -- 로그인을 시도한 IP 주소 (옵션)
                                    device_info VARCHAR(255), -- 로그인을 시도한 디바이스 정보 (옵션)
                                    actionTime TIMESTAMP default CURRENT_TIMESTAMP NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES users(id) -- users는 사용자 테이블 이름이라 가정
);
