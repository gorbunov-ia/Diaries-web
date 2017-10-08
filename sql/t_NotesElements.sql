create table t_NotesElements
(
	ID 				INT 		NOT NULL AUTO_INCREMENT,
	NoteID 			INT 		NOT NULL,
    Description 	NVARCHAR(64) NOT NULL,
	SortBy 			INT 		NOT NULL DEFAULT 0,
    LastModified 	DATETIME 	NOT NULL DEFAULT NOW(),
		
	PRIMARY KEY (ID),
	UNIQUE INDEX (NoteID, Description),
    UNIQUE INDEX (NoteID, SortBy),	
	FOREIGN KEY (NoteID) REFERENCES t_Notes (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
