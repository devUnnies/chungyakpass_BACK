-- SELECT * FROM INP_USER ;
-- SELECT * FROM INP_USER_BANKBOOK ;
-- SELECT * FROM INP_HOUSE ;
-- SELECT * FROM INP_HOUSE_MEMBER ;
-- SELECT * FROM INP_HOUSE_MEMBER_RELATION ;
-- SELECT * FROM INP_HOUSE_MEMBER_PROPERTY ;
-- SELECT * FROM INP_HOUSE_MEMBER_CHUNGYAK ;
-- SELECT * FROM INP_HOUSE_MEMBER_CHUNGYAK_RESTRICTION ;
-- SELECT * FROM APT_INFO ;
-- SELECT * FROM APT_INFO_AMOUNT ;
-- SELECT * FROM APT_INFO_RECEIPT ;
-- SELECT * FROM APT_INFO_TARGET ;
-- SELECT * FROM APT_INFO_TARGET_SPECIAL ;
-- SELECT * FROM STD_BANKBOOK ;
-- SELECT * FROM STD_MONTHLY_AVERAGE_INCOME ;
-- SELECT * FROM STD_PRIORITY_DEPOSIT ;
-- SELECT * FROM STD_PRIORITY_NUMEBER_PAYMENTS ;
-- SELECT * FROM STD_PRIORITY_SUBSCRIPTION_PERIOD ;
-- SELECT * FROM STD_PROPERTY ;
-- SELECT * FROM USER_AUTHORITY ;
-- SELECT * FROM STD_ADDRESS_LEVEL1 ;
-- SELECT * FROM STD_ADDRESS_LEVEL2 ;



INSERT INTO inp_user (USER_ID, EMAIL, PASSWORD, ACTIVATED) VALUES (1, 'admin@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 1);
INSERT INTO inp_user (USER_ID, EMAIL, PASSWORD, ACTIVATED) VALUES (2, 'user@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 1);

INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (2, 'ROLE_USER');

INSERT INTO std_relation (relation_id, relation, only_one_yn, parent_yn, child_yn)
VALUES (1, '본인', 'y', 'n', 'n'),
       (2, '배우자', 'y', 'n', 'n'),
       (3, '부', 'y', 'y', 'n'),
       (4, '모', 'y', 'y', 'n'),
       (5, '배우자의부', 'y', 'y', 'n'),
       (6, '배우자의모', 'y', 'y', 'n'),
       (7, '조부', 'y', 'y', 'n'),
       (8, '조모', 'y', 'y', 'n'),
       (9, '배우자의조부', 'y', 'y', 'n'),
       (10, '배우자의조모', 'y', 'y', 'n'),
       (11, '자녀_일반', 'n', 'n', 'y'),
       (12, '자녀_태아', 'n', 'n', 'y'),
       (13, '자녀의배우자', 'n', 'n', 'y'),
       (14, '손자녀', 'n', 'n', 'y'),
       (15, '손자녀의배우자', 'n', 'n', 'y');


INSERT INTO std_address_level1 (address_level1_id, address_level1, nearby_area, deposit_area, metropolitan_area_yn)
VALUES (1, '서울', 1, '서울부산', 'y'),
       (2, '인천', 1, '기타광역시', 'y'),
       (3, '경기', 1, '기타시군', 'y'),
       (4, '대전', 2, '기타광역시', 'n'),
       (5, '세종', 2, '기타시군', 'n'),
       (6, '충남', 2, '기타시군', 'n'),
       (7, '충북', 3, '기타시군', 'n'),
       (8, '광주', 4, '기타광역시', 'n'),
       (9, '전남', 4, '기타시군', 'n'),
       (10, '전북', 5, '기타시군', 'n'),
       (11, '대구', 6, '기타광역시', 'n'),
       (12, '경북', 6, '기타시군', 'n'),
       (13, '부산', 7, '서울부산', 'n'),
       (14, '울산', 7, '기타광역시', 'n'),
       (15, '경남', 7, '기타시군', 'n'),
       (16, '강원', 8, '기타시군', 'n'),
       (17, '제주', 9, '기타시군', 'n');

INSERT INTO `std_address_level2` (address_level2_id, address_level1_id, address_level2)
VALUES (1, 1, '종로구'),
       (2, 1, '중구'),
       (3, 1, '용산구'),
       (4, 1, '성동구'),
       (5, 1, '광진구'),
       (6, 1, '동대문구'),
       (7, 1, '중랑구'),
       (8, 1, '성북구'),
       (9, 1, '강북구'),
       (10, 1, '도봉구'),
       (11, 1, '노원구'),
       (12, 1, '은평구'),
       (13, 1, '서대문구'),
       (14, 1, '마포구'),
       (15, 1, '양천구'),
       (16, 1, '강서구'),
       (17, 1, '구로구'),
       (18, 1, '금천구'),
       (19, 1, '영등포구'),
       (20, 1, '동작구'),
       (21, 1, '관악구'),
       (22, 1, '서초구'),
       (23, 1, '강남구'),
       (24, 1, '송파구'),
       (25, 1, '강동구'),
       (26, 2, '중구'),
       (27, 2, '동구'),
       (28, 2, '남구'),
       (29, 2, '미추홀구'),
       (30, 2, '연수구'),
       (31, 2, '남동구'),
       (32, 2, '부평구'),
       (33, 2, '계양구'),
       (34, 2, '서구'),
       (35, 2, '강화군'),
       (36, 2, '옹진군'),
       (37, 3, '수원시'),
       (38, 3, '성남시'),
       (39, 3, '고양시'),
       (40, 3, '용인시'),
       (41, 3, '부천시'),
       (42, 3, '안산시'),
       (43, 3, '안양시'),
       (44, 3, '남양주시'),
       (45, 3, '화성시'),
       (46, 3, '평택시'),
       (47, 3, '의정부시'),
       (48, 3, '시흥시'),
       (49, 3, '파주시'),
       (50, 3, '광명시'),
       (51, 3, '김포시'),
       (52, 3, '군포시'),
       (53, 3, '광주시'),
       (54, 3, '이천시'),
       (55, 3, '양주시'),
       (56, 3, '오산시'),
       (57, 3, '구리시'),
       (58, 3, '안성시'),
       (59, 3, '포천시'),
       (60, 3, '의왕시'),
       (61, 3, '하남시'),
       (62, 3, '여주시'),
       (63, 3, '여주군'),
       (64, 3, '양평군'),
       (65, 3, '동두천시'),
       (66, 3, '과천시'),
       (67, 3, '가평군'),
       (68, 3, '연천군'),
       (69, 4, '동구'),
       (70, 4, '중구'),
       (71, 4, '서구'),
       (72, 4, '유성구'),
       (73, 4, '대덕구'),
       (74, 5, '세종특별자치시'),
       (75, 6, '천안시'),
       (76, 6, '공주시'),
       (77, 6, '보령시'),
       (78, 6, '아산시'),
       (79, 6, '서산시'),
       (80, 6, '논산시'),
       (81, 6, '계룡시'),
       (82, 6, '당진시'),
       (83, 6, '당진군'),
       (84, 6, '금산군'),
       (85, 6, '연기군'),
       (86, 6, '부여군'),
       (87, 6, '서천군'),
       (88, 6, '청양군'),
       (89, 6, '홍성군'),
       (90, 6, '예산군'),
       (91, 6, '태안군'),
       (92, 7, '청주시'),
       (93, 7, '충주시'),
       (94, 7, '제천시'),
       (95, 7, '청원군'),
       (96, 7, '보은군'),
       (97, 7, '옥천군'),
       (98, 7, '영동군'),
       (99, 7, '진천군'),
       (100, 7, '괴산군'),
       (101, 7, '음성군'),
       (102, 7, '단양군'),
       (103, 7, '증평군'),
       (104, 8, '동구'),
       (105, 8, '서구'),
       (106, 8, '남구'),
       (107, 8, '북구'),
       (108, 8, '광산구'),
       (109, 9, '목포시'),
       (110, 9, '여수시'),
       (111, 9, '순천시'),
       (112, 9, '나주시'),
       (113, 9, '광양시'),
       (114, 9, '담양군'),
       (115, 9, '곡성군'),
       (116, 9, '구례군'),
       (117, 9, '고흥군'),
       (118, 9, '보성군'),
       (119, 9, '화순군'),
       (120, 9, '장흥군'),
       (121, 9, '강진군'),
       (122, 9, '해남군'),
       (123, 9, '영암군'),
       (124, 9, '무안군'),
       (125, 9, '함평군'),
       (126, 9, '영광군'),
       (127, 9, '장성군'),
       (128, 9, '완도군'),
       (129, 9, '진도군'),
       (130, 9, '신안군'),
       (131, 10, '전주시'),
       (132, 10, '군산시'),
       (133, 10, '익산시'),
       (134, 10, '정읍시'),
       (135, 10, '남원시'),
       (136, 10, '김제시'),
       (137, 10, '완주군'),
       (138, 10, '진안군'),
       (139, 10, '무주군'),
       (140, 10, '장수군'),
       (141, 10, '임실군'),
       (142, 10, '순창군'),
       (143, 10, '고창군'),
       (144, 10, '부안군'),
       (145, 11, '중구'),
       (146, 11, '동구'),
       (147, 11, '서구'),
       (148, 11, '남구'),
       (149, 11, '북구'),
       (150, 11, '수성구'),
       (151, 11, '달서구'),
       (152, 11, '달성군'),
       (153, 12, '포항시'),
       (154, 12, '경주시'),
       (155, 12, '김천시'),
       (156, 12, '안동시'),
       (157, 12, '구미시'),
       (158, 12, '영주시'),
       (159, 12, '영천시'),
       (160, 12, '상주시'),
       (161, 12, '문경시'),
       (162, 12, '경산시'),
       (163, 12, '군위군'),
       (164, 12, '의성군'),
       (165, 12, '청송군'),
       (166, 12, '영양군'),
       (167, 12, '영덕군'),
       (168, 12, '청도군'),
       (169, 12, '고령군'),
       (170, 12, '성주군'),
       (171, 12, '칠곡군'),
       (172, 12, '예천군'),
       (173, 12, '봉화군'),
       (174, 12, '울진군'),
       (175, 12, '울릉군'),
       (176, 13, '중구'),
       (177, 13, '서구'),
       (178, 13, '동구'),
       (179, 13, '영도구'),
       (180, 13, '부산진구'),
       (181, 13, '동래구'),
       (182, 13, '남구'),
       (183, 13, '북구'),
       (184, 13, '해운대구'),
       (185, 13, '사하구'),
       (186, 13, '금정구'),
       (187, 13, '강서구'),
       (188, 13, '연제구'),
       (189, 13, '수영구'),
       (190, 13, '사상구'),
       (191, 13, '기장군'),
       (192, 14, '중구'),
       (193, 14, '남구'),
       (194, 14, '동구'),
       (195, 14, '북구'),
       (196, 14, '울주군'),
       (197, 15, '창원시'),
       (198, 15, '마산시'),
       (199, 15, '진주시'),
       (200, 15, '진해시'),
       (201, 15, '통영시'),
       (202, 15, '사천시'),
       (203, 15, '김해시'),
       (204, 15, '밀양시'),
       (205, 15, '거제시'),
       (206, 15, '양산시'),
       (207, 15, '의령군'),
       (208, 15, '함안군'),
       (209, 15, '창녕군'),
       (210, 15, '고성군'),
       (211, 15, '남해군'),
       (212, 15, '하동군'),
       (213, 15, '산청군'),
       (214, 15, '함양군'),
       (215, 15, '거창군'),
       (216, 15, '합천군'),
       (217, 16, '춘천시'),
       (218, 16, '원주시'),
       (219, 16, '강릉시'),
       (220, 16, '동해시'),
       (221, 16, '태백시'),
       (222, 16, '속초시'),
       (223, 16, '삼척시'),
       (224, 16, '홍천군'),
       (225, 16, '횡성군'),
       (226, 16, '영월군'),
       (227, 16, '평창군'),
       (228, 16, '정선군'),
       (229, 16, '철원군'),
       (230, 16, '화천군'),
       (231, 16, '양구군'),
       (232, 16, '인제군'),
       (233, 16, '고성군'),
       (234, 16, '양양군'),
       (235, 17, '제주시'),
       (236, 17, '서귀포시');


INSERT INTO std_monthly_average_income (monthly_average_income_id, application_public_housing_special_laws,
                                        special_supply, supply, dual_income, monthly_average_income_excess,
                                        monthly_average_income_below,
                                        average_monthly_income3people_less_excess,
                                        average_monthly_income3people_less_below,
                                        average_monthly_income4people_less_excess,
                                        average_monthly_income4people_less_below,
                                        average_monthly_income5people_less_excess,
                                        average_monthly_income5people_less_below,
                                        average_monthly_income6people_less_excess,
                                        average_monthly_income6people_less_below,
                                        average_monthly_income7people_less_excess,
                                        average_monthly_income7people_less_below,
                                        average_monthly_income8people_less_excess,
                                        average_monthly_income8people_less_below)
VALUES (1, 'y', '다자녀가구', null, null, 0, 120, 0, 7236192, 0, 8513046, 0, 8513046, 0, 8872376, 0, 93336280, 0, 9794879),
        (2, 'y', '노부모부양', null, null, 0, 120, 0, 7236192, 0, 8513046, 0, 8513046, 0, 8872376, 0, 93336280, 0, 9794879),
        (3, 'n', '신혼부부', '우선공급', 'n', 0, 100, 0, 6030160, 0, 7094205, 0, 7094205, 0, 7393647, 0, 7778023, 0, 8162399),
        (4, 'n', '신혼부부', '우선공급', 'y', 100, 120, 6030161, 7236192, 7094206, 8513046, 7094206, 8513046, 7393648, 8872376,
        7778024, 9333628, 8162400, 9794879),
        (5, 'n', '신혼부부', '일반공급', 'n', 100, 140, 6030161, 8442224, 7094206, 9931887, 7094206, 9931887, 7393648, 10351106,
        7778024, 10889232, 8162400, 11427359),
        (6, 'n', '신혼부부', '일반공급', 'y', 120, 160, 7236193, 9648256, 8513047, 11350728, 8513047, 11350728, 8872377,
        11829835, 9333629, 12444837, 9794880, 13059838),
        (7, 'y', '신혼부부', '우선공급', 'n', 0, 100, 0, 6030160, 0, 7094205, 0, 7094205, 0, 7393647, 0, 7778023, 0, 8162399),
        (8, 'y', '신혼부부', '우선공급', 'y', 100, 120, 6030161, 7236192, 7094206, 8513046, 7094206, 8513046, 7393648, 8872376,
        7778024, 9333628, 8162400, 9794879),
        (9, 'y', '신혼부부', '일반공급', 'n', 100, 130, 6030161, 7839208, 7094206, 9222467, 7094206, 9222467, 7393648,
        9611741, 7778024, 10111430, 8162400, 10611119),
        (10, 'y', '신혼부부', '일반공급', 'y', 120, 140, 7236193, 8442224, 8513047, 9931887, 8513047, 9931887, 8872377,
        10351106, 9333629, 10889232, 9797880, 11427359),
        (11, 'n', '생애최초', '우선공급', null, 0, 130, 0, 7839208, 0, 9222467, 0, 9222467, 0, 9611741, 0,
        10111430, 0, 10611119),
        (12, 'n', '생애최초', '일반공급', null, 130, 160, 7839209, 9648256, 9222468, 11350728, 9222468, 11350728, 9611742,
        11829835, 10111431, 12444837, 10611120, 13059838),
        (13, 'y', '생애최초', '우선공급', null, 0, 100, 0, 6030160, 0, 7094205, 0, 7094205, 0,
        7393647, 0, 7778023, 0, 8162399),
        (14, 'y', '생애최초', '일반공급', null, 100, 130, 6030161, 7839208, 7094206, 9222467, 7094206, 9222467, 7393648,
        9611741, 7778024, 10111430, 8162400, 10611119),
        (15, 'y', '신혼부부', '특별공급가점', 'n', 0,80, 0, 4824128, 0, 5675364, 0, 5675364,0,6016838,0,6358313 ,0,6699787),
        (16, 'y', '신혼부부', '특별공급가점', 'y', 80, 100, 4824128, 6030160, 5675364, 7094205, 5675364, 7094205, 6016838, 7393647, 6358313, 7778023, 6699787, 8162399);



INSERT INTO STD_PRIORITY_NUMEBER_PAYMENTS (PRIORITY_NUMEBER_PAYMENTS_ID, SUPPLY, SPECIAL_SUPPLY, SPECULATION_OVERHEATED,
                                           SUBSCRIPTION_OVERHEATED, ATROPHY_AREA, METROPOLITAN_AREA_YN, COUNT_PAYMENTS)
VALUES (1, '일반공급', NULL, 'y', 'y', 'n', 'y', 24),
       (2, '일반공급', NULL, 'y', 'y', 'n', 'n', 24),
       (3, '일반공급', NULL, 'y', 'n', 'n', 'n', 24),
       (4, '일반공급', NULL, 'y', 'n', 'n', 'y', 24),
       (5, '일반공급', NULL, 'n', 'y', 'n', 'y', 24),
       (6, '일반공급', NULL, 'n', 'y', 'n', 'n', 24),
       (7, '일반공급', NULL, 'n', 'n', 'y', 'y', 1),
       (8, '일반공급', NULL, 'n', 'n', 'y', 'n', 1),
       (9, '일반공급', NULL, 'n', 'n', 'n', 'y', 12),
       (10, '일반공급', NULL, 'n', 'n', 'n', 'n', 6),
       (11, '특별공급', '노부모부양', 'y', 'y', 'n', 'y', 24),
       (12, '특별공급', '노부모부양', 'y', 'y', 'n', 'n', 24),
       (13, '특별공급', '노부모부양', 'y', 'n', 'n', 'y', 24),
       (14, '특별공급', '노부모부양', 'y', 'n', 'n', 'n', 24),
       (15, '특별공급', '노부모부양', 'n', 'y', 'n', 'y', 24),
       (16, '특별공급', '노부모부양', 'n', 'y', 'n', 'n', 24),
       (17, '특별공급', '노부모부양', 'n', 'n', 'y', 'y', 1),
       (18, '특별공급', '노부모부양', 'n', 'n', 'y', 'n', 1),
       (19, '특별공급', '노부모부양', 'n', 'n', 'n', 'y', 12),
       (20, '특별공급', '노부모부양', 'n', 'n', 'n', 'n', 6),
       (21, '특별공급', '생애최초', 'y', 'y', 'n', 'y', 24),
       (22, '특별공급', '생애최초', 'y', 'y', 'n', 'n', 24),
       (23, '특별공급', '생애최초', 'y', 'n', 'n', 'y', 24),
       (24, '특별공급', '생애최초', 'y', 'n', 'n', 'n', 24),
       (25, '특별공급', '생애최초', 'n', 'y', 'n', 'y', 24),
       (26, '특별공급', '생애최초', 'n', 'y', 'n', 'n', 24),
       (27, '특별공급', '생애최초', 'n', 'n', 'y', 'y', 1),
       (28, '특별공급', '생애최초', 'n', 'n', 'y', 'n', 1),
       (29, '특별공급', '생애최초', 'n', 'n', 'n', 'y', 12),
       (30, '특별공급', '생애최초', 'n', 'n', 'n', 'n', 6),
       (31, '특별공급', '기관추천', 'y', 'y', 'n', 'y', 6),
       (32, '특별공급', '기관추천', 'y', 'y', 'n', 'n', 6),
       (33, '특별공급', '기관추천', 'y', 'n', 'n', 'y', 6),
       (34, '특별공급', '기관추천', 'y', 'n', 'n', 'n', 6),
       (35, '특별공급', '기관추천', 'n', 'y', 'n', 'y', 6),
       (36, '특별공급', '기관추천', 'n', 'y', 'n', 'n', 6),
       (37, '특별공급', '기관추천', 'n', 'n', 'y', 'y', 1),
       (38, '특별공급', '기관추천', 'n', 'n', 'y', 'n', 1),
       (39, '특별공급', '기관추천', 'n', 'n', 'n', 'y', 6),
       (40, '특별공급', '기관추천', 'n', 'n', 'n', 'n', 6),
       (41, '특별공급', '신혼부부', 'y', 'y', 'n', 'y', 6),
       (42, '특별공급', '신혼부부', 'y', 'y', 'n', 'n', 6),
       (43, '특별공급', '신혼부부', 'y', 'n', 'n', 'y', 6),
       (44, '특별공급', '신혼부부', 'y', 'n', 'n', 'n', 6),
       (45, '특별공급', '신혼부부', 'n', 'y', 'n', 'y', 6),
       (46, '특별공급', '신혼부부', 'n', 'y', 'n', 'n', 6),
       (47, '특별공급', '신혼부부', 'n', 'n', 'y', 'y', 1),
       (48, '특별공급', '신혼부부', 'n', 'n', 'y', 'n', 1),
       (49, '특별공급', '신혼부부', 'n', 'n', 'n', 'y', 6),
       (50, '특별공급', '신혼부부', 'n', 'n', 'n', 'n', 6),
       (51, '특별공급', '다자녀가구', 'y', 'y', 'n', 'y', 6),
       (52, '특별공급', '다자녀가구', 'y', 'y', 'n', 'n', 6),
       (53, '특별공급', '다자녀가구', 'y', 'n', 'n', 'y', 6),
       (54, '특별공급', '다자녀가구', 'y', 'n', 'n', 'n', 6),
       (55, '특별공급', '다자녀가구', 'n', 'y', 'n', 'y', 6),
       (56, '특별공급', '다자녀가구', 'n', 'y', 'n', 'n', 6),
       (57, '특별공급', '다자녀가구', 'n', 'n', 'y', 'y', 1),
       (58, '특별공급', '다자녀가구', 'n', 'n', 'y', 'n', 1),
       (59, '특별공급', '다자녀가구', 'n', 'n', 'n', 'y', 6),
       (60, '특별공급', '다자녀가구', 'n', 'n', 'n', 'n', 6);

INSERT INTO STD_PRIORITY_SUBSCRIPTION_PERIOD(PRIORITY_SUBSCRIPTION_PERIOD_ID, SUPPLY, SPECIAL_SUPPLY,
                                             SPECULATION_OVERHEATED, SUBSCRIPTION_OVERHEATED,ATROPHY_AREA,
                                             METROPOLITAN_AREA_YN, SUBSCRIPTION_PERIOD)
VALUES (1, '일반공급', NULL, 'y', 'y', 'n', 'y', 24),
       (2, '일반공급', NULL, 'y', 'y', 'n', 'y', 24),
       (3, '일반공급', NULL, 'y', 'y', 'n', 'n', 24),
       (4, '일반공급', NULL, 'y', 'n', 'n', 'n', 24),
       (5, '일반공급', NULL, 'y', 'n', 'n', 'y', 24),
       (6, '일반공급', NULL, 'n', 'y', 'n', 'y', 24),
       (7, '일반공급', NULL, 'n', 'n', 'y', 'y', 1),
       (8, '일반공급', NULL, 'n', 'n', 'y', 'n', 1),
       (9, '일반공급', NULL, 'n', 'n', 'n', 'y', 12),
       (10, '일반공급', NULL, 'n', 'n', 'n', 'n', 6),
       (11, '특별공급', '노부모부양', 'y', 'y', 'n', 'y', 24),
       (12, '특별공급', '노부모부양', 'y', 'y', 'n', 'n', 24),
       (13, '특별공급', '노부모부양', 'y', 'n', 'n', 'y', 24),
       (14, '특별공급', '노부모부양', 'y', 'n', 'n', 'n', 24),
       (15, '특별공급', '노부모부양', 'n', 'y', 'n', 'y', 24),
       (16, '특별공급', '노부모부양', 'n', 'y', 'n', 'n', 24),
       (17, '특별공급', '노부모부양', 'n', 'n', 'y', 'y', 1),
       (18, '특별공급', '노부모부양', 'n', 'n', 'y', 'n', 1),
       (19, '특별공급', '노부모부양', 'n', 'n', 'n', 'y', 12),
       (20, '특별공급', '노부모부양', 'n', 'n', 'n', 'n', 6),
       (21, '특별공급', '생애최초', 'y', 'y', 'n', 'y', 24),
       (22, '특별공급', '생애최초', 'y', 'y', 'n', 'n', 24),
       (23, '특별공급', '생애최초', 'y', 'n', 'n', 'y', 24),
       (24, '특별공급', '생애최초', 'y', 'n', 'n', 'n', 24),
       (25, '특별공급', '생애최초', 'n', 'y', 'n', 'y', 24),
       (26, '특별공급', '생애최초', 'n', 'y', 'n', 'n', 24),
       (27, '특별공급', '생애최초', 'n', 'n', 'y', 'y', 1),
       (28, '특별공급', '생애최초', 'n', 'n', 'y', 'n', 1),
       (29, '특별공급', '생애최초', 'n', 'n', 'n', 'y', 12),
       (30, '특별공급', '생애최초', 'n', 'n', 'n', 'n', 6),
       (31, '특별공급', '기관추천', 'y', 'y', 'n', 'y', 6),
       (32, '특별공급', '기관추천', 'y', 'y', 'n', 'n', 6),
       (33, '특별공급', '기관추천', 'y', 'n', 'n', 'y', 6),
       (34, '특별공급', '기관추천', 'y', 'n', 'n', 'n', 6),
       (35, '특별공급', '기관추천', 'n', 'y', 'n', 'y', 6),
       (36, '특별공급', '기관추천', 'n', 'y', 'n', 'n', 6),
       (37, '특별공급', '기관추천', 'n', 'n', 'y', 'y', 1),
       (38, '특별공급', '기관추천', 'n', 'n', 'y', 'n', 1),
       (39, '특별공급', '기관추천', 'n', 'n', 'n', 'y', 6),
       (40, '특별공급', '기관추천', 'n', 'n', 'n', 'n', 6),
       (41, '특별공급', '신혼부부', 'y', 'y', 'n', 'y', 6),
       (42, '특별공급', '신혼부부', 'y', 'y', 'n', 'n', 6),
       (43, '특별공급', '신혼부부', 'y', 'n', 'n', 'y', 6),
       (44, '특별공급', '신혼부부', 'y', 'n', 'n', 'n', 6),
       (45, '특별공급', '신혼부부', 'n', 'y', 'n', 'y', 6),
       (46, '특별공급', '신혼부부', 'n', 'y', 'n', 'n', 6),
       (47, '특별공급', '신혼부부', 'n', 'n', 'y', 'y', 1),
       (48, '특별공급', '신혼부부', 'n', 'n', 'y', 'n', 1),
       (49, '특별공급', '신혼부부', 'n', 'n', 'n', 'y', 6),
       (50, '특별공급', '신혼부부', 'n', 'n', 'n', 'n', 6),
       (51, '특별공급', '다자녀가구', 'y', 'y', 'n', 'y', 6),
       (52, '특별공급', '다자녀가구', 'y', 'y', 'n', 'n', 6),
       (53, '특별공급', '다자녀가구', 'y', 'n', 'n', 'y', 6),
       (54, '특별공급', '다자녀가구', 'y', 'n', 'n', 'n', 6),
       (55, '특별공급', '다자녀가구', 'n', 'y', 'n', 'y', 6),
       (56, '특별공급', '다자녀가구', 'n', 'y', 'n', 'n', 6),
       (57, '특별공급', '다자녀가구', 'n', 'n', 'y', 'y', 1),
       (58, '특별공급', '다자녀가구', 'n', 'n', 'y', 'n', 1),
       (59, '특별공급', '다자녀가구', 'n', 'n', 'n', 'y', 6),
       (60, '특별공급', '다자녀가구', 'n', 'n', 'n', 'n', 6);

insert into STD_BANKBOOK (STD_BANKBOOK_ID, BANKBOOK, NATIONAL_HOUSING_SUPPLY_POSSIBLE,
                          PRIVATE_HOUSING_SUPPLY_IS_POSSIBLE, RESTRICTION_SALE_AREA)
VALUES (1, '주택청약종합저축', 'y', 'y', NULL),
       (2, '청약저축', 'y', 'n', NULL),
       (3, '청약예금', 'n', 'y', NULL),
       (4, '청약부금', 'n', 'y', 85);
