--- pet pals

--- 1,2,3,4
create database petpals;
use petpals;


CREATE TABLE Pets (
    PetID INT PRIMARY KEY,
    Name VARCHAR(100),
    Age INT,
    Breed VARCHAR(100),
    Type VARCHAR(100),
    AvailableForAdoption BIT,
    ShelterID INT,
    FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID)
);

select * from pets;
CREATE TABLE Shelters (
    ShelterID INT PRIMARY KEY,
    Name VARCHAR(100),
    Location VARCHAR(100)
);

CREATE TABLE Donations (
    DonationID INT PRIMARY KEY,
    DonorName VARCHAR(100),
    DonationType VARCHAR(100),
    DonationAmount DECIMAL(10,2), 
    DonationItem VARCHAR(100),
    DonationDate DATETIME,
    ShelterID INT,
    FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID)
);


CREATE TABLE AdoptionEvents (
    EventID INT PRIMARY KEY,
    EventName VARCHAR(100),
    EventDate DATETIME,
    Location VARCHAR(100)
);

CREATE TABLE Participants (
    ParticipantID INT PRIMARY KEY,
    ParticipantName VARCHAR(100),
    ParticipantType VARCHAR(50),  
    EventID INT,
    FOREIGN KEY (EventID) REFERENCES AdoptionEvents(EventID)
);

INSERT INTO Pets (PetID, Name, Age, Breed, Type, AvailableForAdoption,ShelterID)
VALUES
(1,'Sam',3,'Golden Retriever','Dog',1,1),
(2,'Tom',2,'Siamese','Cat',0,2),
(3,'Scooby',4,'Labrador','Dog',0,3),
(4,'Shiny',1,'Persian','Cat',1,1);
 UPDATE Pets set Breed="Persian"
 WHERE Breed="Siamese";
UPDATE Pets set ShelterID="1"
WHERE Breed="Persian";
 UPDATE Pets set ShelterID="2"
 WHERE Breed="Golden Retriever";
 
 select* from pets;
--- 5
SELECT name,age,breed,type from Pets 
WHERE 
AvailableForAdoption=1;

INSERT INTO Shelters (ShelterID, Name, Location)
VALUES
(1,'Paws Shelters','123 street,XYZ city'),
(2,'Here is Paws Shelter','abc street,123 city'),
(3,'Smart shelter','456 down street, abc city');
UPDATE Shelters SET Location="Chennai" 
WHERE Location="abc street,123 city";
SELECT * from Shelters;

INSERT INTO Donations (DonationID, DonorName, DonationType, DonationAmount, DonationItem, DonationDate,ShelterID)
VALUES
(1,'John Doe','Cash',100.00,'Pet Food', '2025-04-02 14:00:00',1),
(2,'Jane Smith','Item',200.00, 'Pet Food', '2025-04-03 09:30:00',2),
(3,'Alice Johnson','Cash',50.00,'Blankets', '2025-04-04 10:15:00',1),
(4,'Bob Brown','Item',100.00,'Blankets', '2025-04-05 15:45:00',3);



SELECT * from Donations;

INSERT INTO AdoptionEvents (EventID, EventName, EventDate, Location)
VALUES
(1,'Paw Adoption Fair','2025-04-12 09:00:00','City Park'),
(2,'Merci Adoption Fair','2025-05-21 11:00:00','Downtown Mall');
UPDATE AdoptionEvents SET Location="Chennai" 
WHERE Location="City Park";
SELECT * FROM AdoptionEvents;

INSERT INTO Participants (ParticipantID, ParticipantName, ParticipantType, EventID)
VALUES
(1,'Paws Shelter','Shelter',1),
(2,'Here is Paws Shelter','Shelter',2),
(3,'Alice Johnson','Adopter',1),
(4,'Jane Smith','Adopter',2);

SELECT * from Participants;

--- 6
SELECT p.ParticipantName, p.ParticipantType
FROM Participants p
JOIN AdoptionEvents e ON e.EventID = p.EventID
WHERE p.EventID = 2;

--- 7
DELIMITER //

CREATE PROCEDURE UpdateShelterInfo(
    IN in_ShelterID INT,
    IN in_NewName VARCHAR(100),
    IN in_NewLocation VARCHAR(100)
)
BEGIN
    IF EXISTS (
        SELECT 1 FROM Shelters WHERE ShelterID = in_ShelterID
    ) THEN
        UPDATE Shelters 
        SET Name = in_NewName,
            Location = in_NewLocation
        WHERE ShelterID = in_ShelterID;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Shelter with the given ID does not exist.';
    END IF;
END //

DELIMITER ;



-- 8
SELECT s.Name AS ShelterName,SUM(d.DonationAmount) AS TotalDonations
FROM Shelters s
LEFT JOIN Donations d ON s.ShelterID = d.ShelterID
GROUP BY s.ShelterID, s.Name;


--- 9
ALTER TABLE Pets
ADD OwnerID INT;

select* from pets;

SELECT Name,Age,Breed,Type
FROM Pets
WHERE OwnerID IS NULL;

--- 10

SELECT DATE_FORMAT(DonationDate, '%M %Y') AS MonthYear,
SUM(DonationAmount) AS TotalDonationAmount
FROM Donations
GROUP BY DATE_FORMAT(DonationDate, '%M %Y')
ORDER BY MIN(DonationDate);

--- 11
SELECT Name,Breed,Age from Pets
WHERE (Age between 1 and 3) or (age>5);

--- 12
SELECT p.Name AS Pet_Name,s.Name AS Shelter_Name
from Pets P 
JOIN Shelters s  ON 
p.ShelterID=s.ShelterID 
WHERE AvailableforAdoption=1;

--- 13
SELECT COUNT(*) AS TotalParticipants
FROM Participants p
JOIN AdoptionEvents e ON p.EventID = e.EventID
JOIN Shelters s ON p.ParticipantName = s.Name
WHERE s.Location LIKE '%Chennai%'
AND p.ParticipantType = 'Shelter';

--- 14
SELECT DISTINCT Breed,age from pets 
WHERE Age BETWEEN 1 and 5; 

--- 15
SELECT * from pets
WHERE AvailableForAdoption = 1;

--- 16

CREATE TABLE User (
    UserID INT PRIMARY KEY,
    UserName VARCHAR(100)
);
INSERT INTO User (UserID, UserName) VALUES
(1, 'Alice Johnson'),
(2, 'Jane Smith'),
(3, 'Bob Brown');

CREATE TABLE Adoption (
    AdoptionID INT PRIMARY KEY,
    PetID INT,
    UserID INT,
    AdoptionDate DATETIME,
    FOREIGN KEY (PetID) REFERENCES Pets(PetID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

INSERT INTO Adoption (AdoptionID, PetID, UserID, AdoptionDate) VALUES
(1, 2, 1, '2025-04-01 10:00:00'),
(2, 3, 2, '2025-04-03 14:30:00');

SELECT p.Name AS petname,u.UserName AS adoptorname
from adoption a
JOIN pets p ON a.PetID=p.PetID
JOIN user u ON a.UserID=u.UserID;

--- 17
SELECT s.Name AS ShelterName, COUNT(p.PetID) AS AvailablePetsCount
FROM Shelters s
left JOIN Pets p ON s.ShelterID = p.ShelterID AND p.AvailableForAdoption = 1
GROUP BY s.ShelterID, s.Name;

--- 18
SELECT p1.Name AS Pet1,p2.Name AS Pet2,p1.Breed,s.Name AS ShelterName
FROM Pets p1
JOIN Pets p2 ON p1.ShelterID = p2.ShelterID AND 
p1.Breed = p2.Breed AND 
p1.PetID < p2.PetID
JOIN Shelters s ON p1.ShelterID = s.ShelterID;

--- 19
SELECT s.Name AS ShelterName,e.EventName AS AdoptionEvent,e.EventDate,
e.Location AS EventLocation
FROM Shelters s
CROSS JOIN AdoptionEvents e;

--- 20
SELECT s.Name AS ShelterName,COUNT(a.AdoptionID) AS TotalAdoptedPets
FROM Adoption a
JOIN Pets p ON a.PetID = p.PetID
JOIN Shelters s ON p.ShelterID = s.ShelterID
GROUP BY s.ShelterID, s.Name
ORDER BY TotalAdoptedPets DESC
LIMIT 2;

SELECT u.username,p.name as petname,p.breed
FROM Adoption a JOIN Pets p ON a.PetID =p.PetID
JOIN User u ON a.UserID =u.UserID
WHERE p.breed="Labrador";