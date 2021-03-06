--安装mysql
--apt-get install mysql-server
--设置密码789456123
--/etc/init.d/mysql start (stop) 为启动和停止服务器
--登陆
--mysql -u root -p
--创建库
--create database huajiao CHARACTER   SET   'utf8' COLLATE   'utf8_general_ci';
--CREATE DATABASE `huajiao1` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
--创建用户并付权
--mysql -u root -p
--CREATE USER 'huajiao'@'%' IDENTIFIED BY 'huajiao';
--Grant all privileges on huajiao.* to huajiao;

--修改外网访问权限
--vim /etc/mysql/mysql.conf.d/mysqld.cnf
--修改bind-address  = 127.0.0.1 为 bind-address  = 0.0.0.0
--在找到[mysqld] 添加
--character_set_server=utf8
--init_connect='SET NAMES utf8'
--使用库
--use  huajiao

--DROP TABLE IF EXISTS t_sun;
  CREATE TABLE t_sun (
    id int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    phone varchar(20) DEFAULT NULL COMMENT '手机编号',
    account varchar(20) DEFAULT NULL COMMENT '账号',
    pwd varchar(100) DEFAULT NULL COMMENT '登录密码',
    create_time  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    state varchar(100) DEFAULT NULL COMMENT '处理状态',
    sun int(10) DEFAULT NULL COMMENT '阳光数',
    PRIMARY KEY (id)
  );
  --DROP TABLE IF EXISTS t_count;
    CREATE TABLE t_count (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      phone varchar(20)  NULL  COMMENT '手机编号',
      account varchar(20) UNIQUE NULL COMMENT '账号',
      pwd varchar(100) DEFAULT NULL COMMENT '登录密码',
      sun_update_time  timestamp  NULL   COMMENT '阳光更新时间',
      dou_update_time  timestamp  NULL   COMMENT '太阳更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      sun int(10) DEFAULT NULL COMMENT '阳光数',
      dou int(10) DEFAULT NULL COMMENT '豆数',
      PRIMARY KEY (id)
    );
  --DROP TABLE IF EXISTS t_register;
    CREATE TABLE t_register (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      register varchar(50)  NULL  COMMENT '注册号',
      phone varchar(20)  NULL  COMMENT '验证码',
      tag varchar(20) UNIQUE NULL COMMENT '自定义标签',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      PRIMARY KEY (id)
    );
--DROP TABLE IF EXISTS t_accout;
    CREATE TABLE t_accout (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      phone varchar(20)  NULL  COMMENT '手机编号',
      number int(10)  NULL COMMENT '账号序号',
      accout varchar(20)  NULL COMMENT '账号',
      pwd varchar(100) DEFAULT NULL COMMENT '登录密码',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      type varchar(100) DEFAULT NULL COMMENT '账号类型',
      PRIMARY KEY (id)
    );
  --DROP TABLE IF EXISTS t_update;
    CREATE TABLE t_update (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      version varchar(200)  NULL  COMMENT '版本',
      remark varchar(200)  NULL  COMMENT '说明',
      url varchar(200) NULL COMMENT '下载地址',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '状态',
      type varchar(100) DEFAULT NULL COMMENT '类型',
      PRIMARY KEY (id)
    );
  --DROP TABLE IF EXISTS t_code;
    CREATE TABLE t_code (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      register varchar(50)  NULL  COMMENT '注册号',
      phone varchar(20)  NULL  COMMENT '验证码',
      tag varchar(20)  NULL COMMENT '自定义标签',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      PRIMARY KEY (id)
    );
  --DROP TABLE IF EXISTS t_user;
    CREATE TABLE t_user (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      accout varchar(20)  NULL COMMENT '账号',
      pwd varchar(100) DEFAULT NULL COMMENT '登录密码',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      type varchar(100) DEFAULT NULL COMMENT '账号类型',
      PRIMARY KEY (id)
    );
    CREATE TABLE t_hongbao_info (
      id int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
      uid varchar(50)  NULL  COMMENT '花椒号',
      update_time  timestamp  NULL   COMMENT '更新时间',
      state varchar(100) DEFAULT NULL COMMENT '账号状态',
      PRIMARY KEY (id)
    );

select * from t_hongbao_info order by id desc

--delete from t_hongbao_info
select sum(dou) from t_count where dou>0 ;

select * from t_count where phone ='94e433225479';

select * from t_register
SELECT id FROM t_register where register='1'

select * from t_accout where type='sun';
select * from t_accout where accout='15889649769'
select * from t_accout where phone='c4c8ba9f4fd2'
select * from t_code

select * from t_accout t where type = 'hongbao' and phone in('11f55ec4f3f2','14d96b41deb6')

SELECT * FROM t_user order by id

SELECT 1 FROM t_user where accout = '123' and pwd = '202cb962ac59075b964b07152d234b70'
--INSERT INTO t_user(accout,pwd,state,update_time) VALUES ('123','202cb962ac59075b964b07152d234b70','1',now());
--delete from t_code
--delete from t_register---
--delete from t_count where phone ='c4c8ba9f4fd2'; where  phone = '' ;
SELECT *,(select tag from t_register where phone=t.phone) as tag,
(select dou from t_count where account=t.accout) as dou,
(select dou_update_time from t_count where account=t.accout) as dou_update_time
from t_accout t where type = 'hongbao' and phone='e2d7275df4f0' order by number;
--commit;
--SELECT (@i:=@i+1) as id , phone , count(phone) count,sum(sun) sun ,sum(dou) dou FROM t_count t ,(select @i:= 0 ) as it group by t.phone order by t.phone;
--select accout,c from (select accout ,count(accout) as c from t_accout where type='hongbao' group by accout )  as a where a.c>=2
--select accout, (select tag from t_register where phone = b.phone) as tag from t_accout b where accout  in(  select accout from (select accout ,count(accout) as c from t_accout where type='hongbao' group by accout )  as a where a.c>=2 ) and type='hongbao'
