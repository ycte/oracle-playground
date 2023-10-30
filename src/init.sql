create database blog_test;
use blog_test;
-- 创建表 m_user、m_blog、m_notify、m_photo
CREATE TABLE `m_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` int(5) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `m_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `content` longtext,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE `m_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,

  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `content` longtext,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4;
CREATE TABLE `m_photo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4;
create table `m_admin` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `status` tinyint(4) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4;
-- 使用触发器记录权限变化
create trigger tadmin after update
on m_user for each row
    insert into m_admin(user_id, status)
        values(NEW.id, NEW.status)
create TABLE `m_commit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  foreign key (blog_id) references m_blog(id)
) DEFAULT CHARSET=utf8mb4;
-- 联表查询
--select c.id, c.blog_id, c.user_id, c.text, c.created, c.status, u.username from m_commit as c left join m_user as u
--on c.user_id = u.id;
-- 初始化表的数据
--select * from m_user;
--select  * from m_blog;
--update m_blog set status=0 where user_id=1;
insert into m_blog values(1,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(2,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(3,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(4,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(5,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(6,1,'abcde','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_blog values(7,1,'abcd','abcde','abcde','2023-06-24T16:03:02',0);
insert into m_user(id, username, avatar, password, status) values (2, 'superop', 'http://',
'96e79218965eb72c92a549dd5a330112', '0');
insert into m_user(id, username, avatar, password, status) values (2, 'op', 'http://',
'96e79218965eb72c92a549dd5a330112', '1');
insert into m_photo(id, user_id, title, content, created, status) values (1,1,'kokomi',
'https://pic4.zhimg.com/80/v2-f6cebed0635edf3a90e844f98103edf3_720w.webp','2023-06-24T16:03:02',0);
insert into m_photo(id, user_id, title, content, created, status) values (2,1,'kokomi',
'https://pic4.zhimg.com/80/v2-f6cebed0635edf3a90e844f98103edf3_720w.webp','2023-06-24T16:03:02',0);
insert into m_notify values(1,1,'欢迎来到 opSpace','opSpace','opSpace','2023-06-24T16:03:02',0);
insert into m_commit values(1,1,2,'sbop','2023-06-24T16:03:02',0);