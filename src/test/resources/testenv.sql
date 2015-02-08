create database hibtestdb;
create user 'hibtestuser'@'localhost' identified by 'hibtestpass';
grant all on hibtestdb.* to hibtestuser@localhost;
create table testtablexml ( id int primary key auto_increment, data varchar(16) );
create table testtablecsv ( id int primary key auto_increment, score int );
