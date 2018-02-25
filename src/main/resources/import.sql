insert into t_Users ( Login ,Pswrd ,Email ,isActive) values ( 'test' ,'$2a$10$lB6/PKg2/JC4XgdMDXyjs.dLC9jFNAuuNbFkL9udcXe/EBjxSyqxW' ,'test@test.com', TRUE);
insert into t_Roles ( Description ) values ('USER'),('ADMIN');
insert into t_UsersRoles ( UserID ,RoleID ) select (select ID from t_Users where Login = 'test') as UserID ,R.ID as RoleID from t_Roles AS R;
insert into t_Notes (UserID ,Description ,LastModified) select U.ID ,D.Description ,CURRENT_DATE() from t_Users AS U cross join ( select 'Тест' as Description ) as D where U.Login = 'test';
insert into t_NotesElements ( NoteID ,Description ,SortBy ,LastModified ) select N.ID ,D.Description ,D.SortBy, CURRENT_DATE() from t_Notes AS N inner join t_Users AS U ON N.UserID = U.ID and U.Login = 'test' cross join ( select 'Тест 1' as Description, 1 as SortBy union all select 'Тест 2' as Description, 2 as SortBy union all select 'Тест 3' as Description, 3 as SortBy ) AS D where N.Description = 'Тест';
