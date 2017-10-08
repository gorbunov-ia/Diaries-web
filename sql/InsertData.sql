insert into t_Users
(
	Login
    ,Pswrd
    ,Email
) values
(
	'viruz'
    ,'' #hash my password
    ,'grbnv.vn@gmail.com'
);

#select * from t_Users

insert into t_Roles
(
	Description
) values
(
	'USER'
),
(
	'ADMIN'
);

#select * from t_Roles

insert into t_UsersRoles
(
	UserID
    ,RoleID
)
select 
	(select ID from t_Users where Login = 'viruz') as UserID
	,R.ID as RoleID	
from t_Roles AS R;

#select * from t_UsersRoles

insert into t_Notes
(
	UserID
    ,Description
)
select 
	U.ID
    ,D.Description
from t_Users AS U
	cross join
    (	select 'Тренировки' as Description
		union all
        select 'Тест' ) as D
where U.Login = 'viruz';

#select * from t_Notes

insert into t_NotesElements
(
	NoteID
    ,Description
    ,SortBy
)
select 
	N.ID
    ,D.Description
    ,D.SortBy
from t_Notes AS N
	inner join
		t_Users AS U
			ON N.UserID = U.ID
            and U.Login = 'viruz'
	cross join
    (	select 'Спина' as Description, 1 as SortBy union all
		select 'Плечи' as Description, 2 as SortBy union all
        select 'Грудь' as Description, 3 as SortBy union all
        select 'Ноги' as Description, 4 as SortBy union all
        select 'Бицепс' as Description, 5 as SortBy union all
        select 'Трицепс' as Description, 6 as SortBy union all
        select 'Трапеция' as Description, 7 as SortBy union all
        select 'Пресс' as Description, 8 as SortBy ) AS D
where N.Description = 'Тренировки';
	
insert into t_NotesElements
(
	NoteID
    ,Description
    ,SortBy
)
select 
	N.ID
    ,D.Description
    ,D.SortBy
from t_Notes AS N
	inner join
		t_Users AS U
			ON N.UserID = U.ID
            and U.Login = 'viruz'
	cross join
    (	select 'Тест 1' as Description, 1 as SortBy union all
		select 'Тест 2' as Description, 2 as SortBy union all
        select 'Тест 3' as Description, 3 as SortBy ) AS D
where N.Description = 'Тест';

#select * from t_NotesElements

