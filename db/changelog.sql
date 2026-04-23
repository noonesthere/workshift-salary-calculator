--liquibase formatted sql


--changeset dzlobenets:create_work_shift
create table work_shift
(
  id                bigint        not null,
  start_date        date          not null,
  begin_at          time          not null,
  finished_at       time          not null,
  bid               decimal(7, 2) not null,
  worked_hours      decimal(4, 2) not null,
  work_shift_salary decimal(7, 2) not null,
  included boolean not null,
  constraint pk_work_shift primary key (id)
);

create index idx_work_shift_start_date
  on work_shift (start_date);

--changeset dzlobenets:create_work_shift_bonus
alter table work_shift add bonus decimal(6, 2) not null default 0.0;
