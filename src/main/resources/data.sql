INSERT INTO INP_USER (USER_ID, EMAIL, PASSWORD, ACTIVATED) VALUES (1, 'admin@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 1);
INSERT INTO INP_USER (USER_ID, EMAIL, PASSWORD, ACTIVATED) VALUES (2, 'user@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (2, 'ROLE_USER');

insert into std_area_leve1(AREA_LEVEL1_ID,AREA_LEVEL1,NEARBY_AREA,DEPOSIT_AMOUNT_AREA,METROPOLITAN_AREA  )  VALUES (1,'서울',1,'서울부산','y'),(2,'인천',1,'기타광역시','y'),(3,'경기',1,'기타시군','y'),(4,'대전',2,'기타광역시','n'),(5,'세종',2,'기타시군','n'),(6,'충남',2,'기타시군','n'),(7,'충북',3,'기타시군','n'),(8,'광주',4,'기타광역시','n'),(9,'전남',4,'기타시군','n'),(10,'전북',5,'기타시군','n'),(11,'대구',6,'기타광역시','n'),(12,'경북',6,'기타시군','n'),(13,'부산',7,'서울부산','n'),(14,'울산',7,'기타광역시','n'),(15,'경남',7,'기타시군','n'),(16,'강원도',8,'기타시군','n'),(17,'제주',9,'기타시군','n');