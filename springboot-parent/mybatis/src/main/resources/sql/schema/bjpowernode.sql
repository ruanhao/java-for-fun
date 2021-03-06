DROP TABLE IF EXISTS EMP;
DROP TABLE IF EXISTS DEPT;
DROP TABLE IF EXISTS SALGRADE;

CREATE TABLE DEPT (
    DEPTNO int(2) not null ,
    DNAME VARCHAR(14) ,
    LOC VARCHAR(13),
    primary key (DEPTNO)
);

CREATE TABLE EMP (
    EMPNO int(4) not null ,
    ENAME VARCHAR(10),
    JOB VARCHAR(9),
    MGR INT(4),
    HIREDATE DATE  DEFAULT NULL,
    SAL DOUBLE(7,2),
    COMM DOUBLE(7,2),
    DEPTNO INT(2),
    primary key (EMPNO)
);

CREATE TABLE SALGRADE (
    GRADE INT,
    LOSAL INT,
    HISAL INT,
    primary key (GRADE)
);