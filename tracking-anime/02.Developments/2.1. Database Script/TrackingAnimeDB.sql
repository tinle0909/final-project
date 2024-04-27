create database TrackingAnimeDB

create table Account(
	AccountID varchar(8) primary key,
	Username varchar(50) not null,
	Password varchar(50) not null,
	Email varchar(100) not null,
	IsAdmin bit default 0,
	Nickname varchar(50) not null,
)

create table ManageAccount(
	AdminID varchar(8) foreign key(AdminID) references Account(AccountID),
	UserID varchar(8) foreign key(UserID) references Account(AccountID),
	ID varchar(8) primary key (AdminID, UserID)
)

create table Anime(
	AnimeID varchar(8) primary key,
	AccountID varchar(8) foreign key(AccountID) references Account(AccountID),
	Title varchar(50) not null,
	Type tinyint,
	Genre tinyint,
	Poster varchar(100),
	Status tinyint,
	Studio nvarchar(20),
	Synopsis nvarchar(200),
	Broadcast datetime,
	Aried date,
	Season varchar(10),
	Episode int not null,
	Nation tinyint,
	Score float not null,
	Rankded int
)

create table TrackingList(
	TLID varchar(8) primary key,
	AccountID varchar(8) foreign key(AccountID) references Account(AccountID),
	CreatedDay Date,
	NumberOfAnime int,
	Mode bit
)

create table TrackingAnime(
	TAID varchar(8) primary key,
	TLID varchar(8) foreign key(TLID) references TrackingList(TLID),
	AnimeID varchar(8) foreign key(AnimeID) references Anime(AnimeID),
	WatchingStatus tinyint,
	LastWatchedEpisode int,
	IsFavorite bit
)

create table Feedback(
	FBID varchar(8) primary key,
	AccountID varchar(8) foreign key(AccountID) references Account(AccountID),
	AnimeID varchar(8) foreign key(AnimeID) references Anime(AnimeID),
	Subject varchar(50) not null,
	Content nvarchar(200) not null,
	Email varchar(100),
	Score int,
	Date Date
)

drop table Feedback

