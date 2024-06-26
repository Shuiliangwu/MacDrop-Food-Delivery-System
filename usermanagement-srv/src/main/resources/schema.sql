DROP 
  TABLE IF EXISTS ADMIN;
CREATE TABLE ADMIN(
  AdminID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 50000000) PRIMARY KEY, 
  Username VARCHAR(255), 
  Password VARCHAR(255), 
  FirstName VARCHAR(255), 
  LastName VARCHAR(255), 
  ETFemail VARCHAR(255)
);

DROP 
  TABLE IF EXISTS Biker;
CREATE TABLE BIKER(
  BikerID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 30000000) PRIMARY KEY, 
  Username VARCHAR(255), 
  Password VARCHAR(255), 
  FirstName VARCHAR(255), 
  LastName VARCHAR(255), 
  CurrentWorkload INT, 
  HistoryWorkload INT, 
  ETFemail VARCHAR(255)
);

DROP 
  TABLE IF EXISTS BOOKSTOREORDER;
CREATE TABLE BOOKSTOREORDER(
  BookStoreOrderID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, 
  CommunityMemberID VARCHAR(255), 
  BookstoreOperatorID VARCHAR(255), 
  BookStoreItems VARCHAR(255), 
  MDDPrice VARCHAR(255), 
  OrderTime VARCHAR(255)
);

DROP 
  TABLE IF EXISTS BOOKSTOREOPERATOR;
CREATE TABLE BOOKSTOREOPERATOR(
  BookstoreOperatorID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 40000000) PRIMARY KEY, 
  Username VARCHAR(255), 
  Password VARCHAR(255), 
  FirstName VARCHAR(255), 
  LastName VARCHAR(255), 
  CurrentMDD VARCHAR(255), 
  ETFemail VARCHAR(255)
);

DROP 
  TABLE IF EXISTS COMMUNITYMEMBER;
CREATE TABLE COMMUNITYMEMBER (
  CommunityMemberID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 10000000) PRIMARY KEY, 
  Username VARCHAR(255), 
  Password VARCHAR(255), 
  FirstName VARCHAR(255), 
  LastName VARCHAR(255), 
  CurrentMDD VARCHAR(255),
  ETFemail VARCHAR(255)
);

DROP 
  TABLE IF EXISTS FOODORDER;
CREATE TABLE FOODORDER(
  FoodOrderID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, 
  CommunityMemberID VARCHAR(255), 
  FoodProviderID VARCHAR(255), 
  FoodItems VARCHAR(255), 
  FoodOrderPrice VARCHAR(255), 
  OrderTime VARCHAR(255), 
  BikerID VARCHAR(255), 
  DropOffLocationID VARCHAR(255), 
  EstimatedPickupTime VARCHAR(255), 
  Status VARCHAR(255)
);

DROP 
  TABLE IF EXISTS FOODPROVIDER;
CREATE TABLE FOODPROVIDER(
  FoodProviderID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 20000000) PRIMARY KEY, 
  Username VARCHAR(255), 
  Password VARCHAR(255), 
  FirstName VARCHAR(255), 
  LastName VARCHAR(255), 
  ETFemail VARCHAR(255)
);

DROP 
  TABLE IF EXISTS LOCATION;
CREATE TABLE LOCATION(
  LocationID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 80000000) PRIMARY KEY, 
  Name VARCHAR(255), 
  CurrentWorkload INT,
  HistoryWorkload INT
);