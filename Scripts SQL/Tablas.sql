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
	news_date datetime,
	comment varchar(1000) constraint comment_comment_nn not null,
	date datetime,
	client varchar(30) constraint comment_client_fk references client (username),
	constraint comment_fk foreign key (author,news_date) references News(author,date),
	constraint comment_pk primary key (author,news_date, date,username)
)

create table FavoriteBand
(
	band varchar(30) constraint band_fk references Band (username),
	client varchar(30) constraint client_fk references Client (username),
	constraint favoriteband_pk primary key (band,client)
)

create table Rating
(
	band varchar(30) constraint rating_band_fk references Band (username),
	client varchar(30) constraint rating_client_fk references Client (username),
	constraint raiting_pk primary key (band,client),
	date datetime constraint rainting_date_nn not null,
	rating numeric(1) constraint rating_rating_nn not null
)

create table Product
(
	id numeric(10) identity(1,1) constraint product_pk primary key,
	band varchar(30) constraint product_band_fk references band(username),
	name varchar(50) constraint product_name_nn not null,
	type varchar(30) constraint product_type_nn not null,
	stock numeric(5) constraint product_stock_nn not null,
	price float constraint product_price_nn not null,
	content varbinary(max) null
)

create table Bill
(
	client varchar(30) constraint bill_client_fk references Client (username),
	product numeric(10) constraint bill_product_fk references Product(id),
	date datetime,
	constraint bill_pk primary key (client,product,date),
	quantity numeric(5) constraint bill_quantity_nn not null,
)

create table Event 
(
	date datetime,
	band varchar(30) constraint event_band_fk references Band (username),
	constraint event_pk primary key (date,band),
	location varchar(100) constraint event_location_nn not null,
	description varchar(500) constraint event_description_nn not null
)

create table SneakPeek
(
	id numeric(10) identity (1,1) constraint sneakpeek_pk primary key,
	band varchar(30) constraint seneakpeek_band_dk references Band (username),
	name varchar(50) constraint sneakpeek_name_nn not null,
	content varbinary(max) constraint sneakpeek_nn not null
)