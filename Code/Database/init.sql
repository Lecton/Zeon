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

-- Table: client

-- DROP TABLE client;

CREATE TABLE client
(
  userid text NOT NULL,
  groupid text NOT NULL DEFAULT '-111'::text,
  "name" text,
  surname text,
  username text NOT NULL,
  email text NOT NULL,
  avatar text DEFAULT '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'::text,
  aboutme text,
  title text,
  registrationdate date,
  loggedin boolean,
  CONSTRAINT client_pkey PRIMARY KEY (userid),
  CONSTRAINT client_email_key UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE client OWNER TO postgres;


-- Table: collection

-- DROP TABLE collection;

CREATE TABLE collection
(
  groupid text NOT NULL,
  ownerid text,
  "name" text,
  CONSTRAINT collection_pkey PRIMARY KEY (groupid),
  CONSTRAINT collection_ownerid_fkey FOREIGN KEY (ownerid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE collection OWNER TO postgres;

-- Table: streamproperty

-- DROP TABLE streamproperty;

CREATE TABLE streamproperty
(
  streamid text NOT NULL,
  userid text,
  "type" text NOT NULL,
  "name" text,
  CONSTRAINT streamproperty_pkey PRIMARY KEY (streamid),
  CONSTRAINT streamproperty_userid_fkey FOREIGN KEY (userid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE streamproperty OWNER TO postgres;

-- Table: streamdata

-- DROP TABLE streamdata;

CREATE TABLE streamdata
(
  streamdataid text NOT NULL,
  streamid text,
  userid text,
  accepted boolean DEFAULT false,
  CONSTRAINT streamdata_pkey PRIMARY KEY (streamdataid),
  CONSTRAINT streamdata_streamid_fkey FOREIGN KEY (streamid)
      REFERENCES streamproperty (streamid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT streamdata_userid_fkey FOREIGN KEY (userid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE streamdata OWNER TO postgres;

-- Table: messeges

-- DROP TABLE messeges;

CREATE TABLE messeges
(
  messageid text NOT NULL,
  userid text,
  targetid text,
  messagedate date,
  message text NOT NULL,
  CONSTRAINT messeges_pkey PRIMARY KEY (messageid),
  CONSTRAINT messeges_targetid_fkey FOREIGN KEY (targetid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT messeges_userid_fkey FOREIGN KEY (userid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE messeges OWNER TO postgres;

-- Table: logger

-- DROP TABLE logger;

CREATE TABLE logger
(
  logid text NOT NULL,
  logdate date,
  userid text,
  "type" text NOT NULL,
  message text NOT NULL,
  CONSTRAINT logger_pkey PRIMARY KEY (logid),
  CONSTRAINT logger_userid_fkey FOREIGN KEY (userid)
      REFERENCES client (userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE logger OWNER TO postgres;

--Match simple to exact.