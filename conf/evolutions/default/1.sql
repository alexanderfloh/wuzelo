# wuzelo schema
 
# --- !Ups

CREATE SEQUENCE player_id_seq;
CREATE SEQUENCE game_id_seq;

CREATE TABLE players (
	id integer NOT NULL DEFAULT nextval('player_id_seq'),
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
	team1player1 integer NOT NULL,
	team1player2 integer NOT NULL,
	team2player1 integer NOT NULL,
	team2player2 integer NOT NULL,
	winner tinyint NOT NULL
);


# --- !Downs

DROP TABLE games;
DROP TABLE players;

DROP SEQUENCE player_id_seq;
DROP SEQUENCE game_id_seq;