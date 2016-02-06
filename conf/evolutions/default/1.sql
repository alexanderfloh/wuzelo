# wuzelo schema
 
# --- !Ups

CREATE TABLE players (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	firstName varchar(255),
	lastName varchar(255),
	elo integer,
	lastElo integer,
	rd integer,
	lastRd integer,
	playedGames integer,
	lastGame TIMESTAMP
);

CREATE TABLE games (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	team1player1 INTEGER NOT NULL,
	team1player2 INTEGER NOT NULL,
	team2player1 INTEGER NOT NULL,
	team2player2 INTEGER NOT NULL,
	timestamp TIMESTAMP NOT NULL
);


# --- !Downs

DROP TABLE games;
DROP TABLE players;

