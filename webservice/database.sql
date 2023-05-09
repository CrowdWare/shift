CREATE TABLE account 
(
    uuid CHAR(52) NOT NULL PRIMARY KEY,
    ruuid CHAR(52) NOT NULL,
    name VARCHAR(250) NOT NULL,
    scooping BIGINT NOT NULL,
    country VARCHAR(30) NOT NULL,
    language VARCHAR(10) NOT NULL,
    FOREIGN KEY (ruuid) REFERENCES account(uuid)
);
