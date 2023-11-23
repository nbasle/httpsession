-- Cleanup
DELETE FROM T_ORDER_LINE;
DELETE FROM T_ORDER;
DELETE FROM T_CUSTOMER;
DELETE FROM T_ITEM;
DELETE FROM T_PRODUCT;
DELETE FROM T_CATEGORY;
DELETE FROM T_COUNTER;

-- Load
INSERT INTO T_CATEGORY VALUES ('FISH', 'Fish', 'Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body' );
INSERT INTO T_CATEGORY VALUES ('DOGS', 'Dogs', 'A domesticated carnivorous mammal related to the foxes and wolves and raised in a wide variety of breeds' );
INSERT INTO T_CATEGORY VALUES ('REPTILES', 'Reptiles', 'Any of various cold-blooded, usually egg-laying vertebrates, such as a snake, lizard, crocodile, turtle' );
INSERT INTO T_CATEGORY VALUES ('CATS', 'Cats', ' Small carnivorous mammal domesticated since early times as a catcher of rats and mice and as a pet and existing in several distinctive breeds and varieties' );
INSERT INTO T_CATEGORY VALUES ('BIRDS', 'Birds', 'Any of the class Aves of warm-blooded, egg-laying, feathered vertebrates with forelimbs modified to form wings' );

INSERT INTO T_PRODUCT VALUES ('FISW01', 'Angelfish', 'Saltwater fish from Australia', 'FISH');
INSERT INTO T_PRODUCT VALUES ('FISW02', 'Tiger Shark', 'Saltwater fish from Australia', 'FISH');
INSERT INTO T_PRODUCT VALUES ('FIFW01', 'Koi', 'Freshwater fish from Japan', 'FISH');
INSERT INTO T_PRODUCT VALUES ('FIFW02', 'Goldfish', 'Freshwater fish from China', 'FISH');

INSERT INTO T_PRODUCT VALUES ('K9BD01', 'Bulldog', 'Friendly dog from England', 'DOGS');
INSERT INTO T_PRODUCT VALUES ('K9PO02', 'Poodle', 'Cute dog from France', 'DOGS');
INSERT INTO T_PRODUCT VALUES ('K9DL01', 'Dalmation', 'Great dog for a fire station', 'DOGS');
INSERT INTO T_PRODUCT VALUES ('K9RT01', 'Golden Retriever', 'Great family dog', 'DOGS');
INSERT INTO T_PRODUCT VALUES ('K9RT02', 'Labrador Retriever', 'Great hunting dog', 'DOGS');
INSERT INTO T_PRODUCT VALUES ('K9CW01', 'Chihuahua', 'Great companion dog', 'DOGS');

INSERT INTO T_PRODUCT VALUES ('RPSN01', 'Rattlesnake', 'Doubles as a watch dog', 'REPTILES');
INSERT INTO T_PRODUCT VALUES ('RPLI02', 'Iguana', 'Friendly green friend', 'REPTILES');

INSERT INTO T_PRODUCT VALUES ('FLDSH01', 'Manx', 'Great for reducing mouse populations', 'CATS');
INSERT INTO T_PRODUCT VALUES ('FLDLH02', 'Persian', 'Friendly house cat, doubles as a princess', 'CATS');

INSERT INTO T_PRODUCT VALUES ('AVCB01', 'Amazon Parrot', 'Great companion for up to 75 years', 'BIRDS');
INSERT INTO T_PRODUCT VALUES ('AVSB02', 'Finch', 'Great stress reliever', 'BIRDS');

INSERT INTO T_ITEM  VALUES ('EST1', 'Large', '10.00', 'FISW01', 'fish1.jpg');
INSERT INTO T_ITEM  VALUES ('EST2', 'Thootless', '10.00', 'FISW01', 'fish1.jpg');
INSERT INTO T_ITEM  VALUES ('EST3', 'Spotted', '12.00', 'FISW02', 'fish4.jpg');
INSERT INTO T_ITEM  VALUES ('EST4', 'Spotless', '12.00', 'FISW02', 'fish4.jpg');
INSERT INTO T_ITEM  VALUES ('EST5', 'Male Adult', '12.00', 'FIFW01', 'fish3.jpg');
INSERT INTO T_ITEM  VALUES ('EST6', 'Female Adult', '12.00', 'FIFW01', 'fish3.jpg');
INSERT INTO T_ITEM  VALUES ('EST7', 'Male Puppy', '12.00', 'FIFW02', 'fish2.jpg');
INSERT INTO T_ITEM  VALUES ('EST8', 'Female Puppy', '12.00', 'FIFW02', 'fish2.jpg');

INSERT INTO T_ITEM  VALUES ('EST9', 'Spotless Male Puppy', '22.00', 'K9BD01', 'dog1.jpg');
INSERT INTO T_ITEM  VALUES ('EST10', 'Spotless Female Puppy', '22.00', 'K9BD01', 'dog1.jpg');
INSERT INTO T_ITEM  VALUES ('EST11', 'Spotted Male Puppy', '32.00', 'K9PO02', 'dog2.jpg');
INSERT INTO T_ITEM  VALUES ('EST12', 'Spotted Female Puppy', '32.00', 'K9PO02', 'dog2.jpg');
INSERT INTO T_ITEM  VALUES ('EST13', 'Tailed', '62.00', 'K9DL01', 'dog3.jpg');
INSERT INTO T_ITEM  VALUES ('EST14', 'Tailless', '62.00', 'K9DL01', 'dog3.jpg');
INSERT INTO T_ITEM  VALUES ('EST15', 'Tailed', '82.00', 'K9RT01', 'dog4.jpg');
INSERT INTO T_ITEM  VALUES ('EST16', 'Tailless', '82.00', 'K9RT01', 'dog4.jpg');
INSERT INTO T_ITEM  VALUES ('EST17', 'Tailed', '100.00', 'K9RT02', 'dog5.jpg');
INSERT INTO T_ITEM  VALUES ('EST18', 'Tailless', '100.00', 'K9RT02', 'dog5.jpg');
INSERT INTO T_ITEM  VALUES ('EST19', 'Female Adult', '100.00', 'K9CW01', 'dog6.jpg');
INSERT INTO T_ITEM  VALUES ('EST20', 'Female Adult', '100.00', 'K9CW01', 'dog6.jpg');

INSERT INTO T_ITEM  VALUES ('EST21', 'Female Adult', '20.00', 'RPSN01', 'snake1.jpg');
INSERT INTO T_ITEM  VALUES ('EST22', 'Male Adult', '20.00', 'RPSN01', 'snake1.jpg');
INSERT INTO T_ITEM  VALUES ('EST211', 'Female Adult', '20.00', 'RPLI02', 'lizard1.jpg');
INSERT INTO T_ITEM  VALUES ('EST221', 'Male Adult', '20.00', 'RPLI02', 'lizard1.jpg');

INSERT INTO T_ITEM  VALUES ('EST23', 'Male Adult', '120.00','FLDSH01', 'cat1.jpg');
INSERT INTO T_ITEM  VALUES ('EST24', 'Female Adult', '120.00', 'FLDSH01', 'cat1.jpg');
INSERT INTO T_ITEM  VALUES ('EST231', 'Male Adult', '75.00','FLDLH02', 'cat2.jpg');
INSERT INTO T_ITEM  VALUES ('EST241', 'Female Adult', '80.00', 'FLDLH02', 'cat2.jpg');

INSERT INTO T_ITEM  VALUES ('EST25', 'Male Adult', '120.00', 'AVCB01', 'bird2.jpg');
INSERT INTO T_ITEM  VALUES ('EST26', 'Female Adult', '120.00', 'AVCB01', 'bird2.jpg');
INSERT INTO T_ITEM  VALUES ('EST27', 'Male Adult', '80.00', 'AVSB02', 'bird1.jpg');
INSERT INTO T_ITEM  VALUES ('EST28', 'Female Adult', '70.00', 'AVSB02', 'bird1.jpg');

INSERT INTO T_CUSTOMER VALUES ('marc123', 'Marc', 'Fleury', '545 123 45', '65 Ritherdon Road', '', 'Los Angeles', 'LA', '56421', 'USA', '', '', '', '','marc123');
INSERT INTO T_CUSTOMER VALUES ('bill000', 'Bill', 'Gates', '654 046 12', '27 West Side', 'Story', 'Alhabama', 'TX', '8401', 'USA', '1245 4897 4567 8417', 'VISA', '02/08', 'bill.gates@microsoft.com', 'bill000');
INSERT INTO T_CUSTOMER VALUES ('job5', 'Steve', 'Jobs', '548 157 15', '154 Star Boulevard', '', 'San Francisco', 'WC', '5455', 'USA', '', '', '', 'steve.jobs@apple.com', 'job5');
