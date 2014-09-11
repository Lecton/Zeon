-- Database: stream2me
/*
DROP DATABASE stream2me;

CREATE DATABASE stream2me
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;
*/

CREATE TABLE Client(
	userID	TEXT NOT NULL UNIQUE,
	groupID TEXT NOT NULL DEFAULT '-111',
	name TEXT,
	surname TEXT,
	username TEXT NOT NULL,
	email	TEXT NOT NULL UNIQUE,
	avatar	TEXT,
	aboutMe	TEXT,
	title	TEXT,
	registrationDate	DATE,
	LoggedIn BOOLEAN,
	PRIMARY KEY(userID)
);

CREATE TABLE Collection(
	groupID TEXT NOT NULL UNIQUE,
	ownerID TEXT REFERENCES Client(userID), 
	name TEXT,
	PRIMARY KEY(groupID)
);

CREATE TABLE StreamProperty(
	streamID TEXT NOT NULL UNIQUE,
	userID TEXT REFERENCES Client(userID),
	type TEXT NOT NULL,
	name TEXT,
	PRIMARY KEY(streamID)
);

CREATE TABLE StreamData(
	streamDataID TEXT NOT NULL UNIQUE,
	streamID TEXT REFERENCES StreamProperty(streamID),
	userID TEXT REFERENCES Client(userID),
	accepted BOOLEAN DEFAULT FALSE,
	PRIMARY KEY(streamDataID)
);

CREATE TABLE Messeges(
	MessageID TEXT NOT NULL UNIQUE,
	userID TEXT REFERENCES Client(userID),
	targetID TEXT REFERENCES Client(userID),
	messageDate DATE,
	message TEXT NOT NULL,
	PRIMARY KEY(MessageID)
);

CREATE TABLE Logger(
	logID TEXT NOT NULL UNIQUE,
	logDate DATE,
	userID TEXT REFERENCES Client(userID),
	type TEXT NOT NULL,
	message TEXT NOT NULL,
	PRIMARY KEY(logID)
);