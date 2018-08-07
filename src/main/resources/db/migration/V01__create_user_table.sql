create table user(
	`id` int primary key auto_increment,
  `name` varchar(255) unique,
  `password` varchar(255),
  `deleted` int default 0
);
