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
--default-character-set=utf8
--init_connect='SET NAMES utf8'
--使用库
--use  huajiao

DROP TABLE IF EXISTS t_sun;
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
  DROP TABLE IF EXISTS t_count;
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



select sum(dou) from t_count ;

--delete from  t_count where  phone = 'x-001' ;
--commit;
--SELECT (@i:=@i+1) as id , phone , count(phone) count,sum(sun) sun ,sum(dou) dou FROM t_count t ,(select @i:= 0 ) as it group by t.phone order by t.phone;