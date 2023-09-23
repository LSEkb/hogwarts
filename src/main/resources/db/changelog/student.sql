--liquibase formatted sql

--changeset laserova:2
create index student_name_index on student(name)