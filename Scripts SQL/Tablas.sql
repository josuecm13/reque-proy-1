create table Account
(
	username varchar(30) constraint Account_pk primary key,
	password varchar(30) constraint account_password_nn not null,
	profile_photo varbinary(max) null
)

create table Administrator
(
	username varchar(30) constraint Administrator_pk primary key
	constraint administrator_username_fk references Account (username)
)


create table Client
(
	username varchar(30) constraint Client_pk primary key constraint client_username_fk references Account (username),
	name varchar(40) constraint client_name_nn not null
	
)
create table Band
(
	username varchar(30) constraint Band_pk primary key constraint band_username_fk references Account (username),
	description varchar(100) constraint band_description_nn not null,
	banner_photo varbinary(max) null,
	rating numeric(3,2) constraint band_rating_nn not null constraint band_rating_default default (5)
)

create table News
(
	author varchar(30) constraint new_username_fk references Account (username),
	date datetime constraint news_date_nn not null,
	title varchar(100) constraint news_title_nn not null,
	body varchar(4000) constraint body_body_nn not null,
	photo varbinary(max) null,
	constraint news_pk primary key (author,date)

)

create table Comment
(
	author varchar(30),
	date datetime,
	constraint comment

)