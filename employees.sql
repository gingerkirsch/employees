create table employee (
    id         bigint       not null,
    email      varchar(255) null,
    first_name varchar(255) null,
    image      varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null,
    salary     float        not null,
    username   varchar(255) not null unique
) engine = MyISAM;

insert into employee (id, email, first_name, image, last_name, password, salary, username) VALUES
(1, 'alex90@test.com', 'Alex', null, 'Brown', 'changeme', 10000.00, 'alex90'),
(2, 'jbg@test.com', 'John', null, 'Doe', 'qwerty', 10000.00, 'johnny_b_good');

create table hibernate_sequence (
    next_val bigint null
)    engine = MyISAM;

--
-- Dumping data for table `hibernate_sequence`
--

insert into hibernate_sequence (next_val) VALUES (1);
alter table employee add primary key (id);

alter table employee
  MODIFY id int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;