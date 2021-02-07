CREATE TABLE account 
(
    uuid CHAR(52) NOT NULL PRIMARY KEY,
    ruuid CHAR(52) NOT NULL,
    name VARCHAR(250) NOT NULL,
    scooping BIGINT NOT NULL,
    FOREIGN KEY (ruuid) REFERENCES account(uuid)
);