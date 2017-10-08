create table t_Roles
(
	ID			INT 		NOT NULL AUTO_INCREMENT,
	description NVARCHAR(32) NOT NULL,

	PRIMARY KEY (ID),
	UNIQUE KEY (Description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
