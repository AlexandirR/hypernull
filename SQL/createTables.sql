CREATE TABLE MatchInfo(
id VARCHAR(255) PRIMARY KEY,
coinsSpawn INT NOT NULL,
mode VARCHAR(255) NOT NULL,
kills INT);

CREATE TABLE BotInfo(
id INT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
coins INT,
kills INT,
deaths INT);

CREATE TABLE MatchTable(
idMatch VARCHAR(255) NOT NULL,
idBot INT NOT NULL, 
coins INT NOT NULL, 
kills INT, 
deaths INT, fieldsOpen INT, 
FOREIGN KEY (idMatch) REFERENCES MatchInfo(id),
FOREIGN KEY (idBot) REFERENCES BotInfo(id));