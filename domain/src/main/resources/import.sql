--
-- Inserting necessary data into table `role`
--

INSERT INTO role VALUES (1, "ROLE_SPEAKER"),(2, "ROLE_ORGANISER");

UPDATE role_seq SET next_val = 3 WHERE next_val = 1;

--
-- Inserting necessary data into table `contact_type`
--

INSERT INTO contact_type VALUES (1, "LinkedIn"),(2, "Twitter"),(3, "FaceBook"),(4, "Blog");

UPDATE contact_type_seq SET next_val = 6 WHERE next_val = 1;

--
-- Inserting necessary data into table `topic`
--

INSERT INTO topic VALUES (1, "JVM Languages and new programming paradigms");
INSERT INTO topic VALUES (2, "Web development and Java Enterprise technologies");
INSERT INTO topic VALUES (3, "Software engineering practices");
INSERT INTO topic VALUES (4, "Architecture & Cloud");
INSERT INTO topic VALUES (5, "BigData & NoSQL");

UPDATE topic_seq SET next_val = 6 WHERE next_val = 1;

--
-- Inserting necessary data into table `type`
--

INSERT INTO `type` VALUES (1, "Regular Talk");
INSERT INTO `type` VALUES (2, "Lighting Talk");
INSERT INTO `type` VALUES (3, "Online Talk");
INSERT INTO `type` VALUES (4, "Hands-On-Lab");

UPDATE type_seq SET next_val = 5 WHERE next_val = 1;

--
-- Inserting necessary data into table `level`
--

INSERT INTO `level` VALUES (1, "Beginner");
INSERT INTO `level` VALUES (2, "Intermediate");
INSERT INTO `level` VALUES (3, "Advanced");
INSERT INTO `level` VALUES (4, "Expert");

UPDATE level_seq SET next_val = 5 WHERE next_val = 1;

--
-- Inserting necessary data into table `status`
--

INSERT INTO `status` VALUES (1, "New");

UPDATE status_seq SET next_val = 2 WHERE next_val = 1;

--
-- Inserting necessary data into table `language`
--

INSERT INTO `language` VALUES (1, "English"), (2, "Ukrainian"), (3, "Russian");

UPDATE language_seq SET next_val = 4 WHERE next_val = 3;

--
-- Setting default user with role SPEAKER with all fields
--

INSERT INTO user_info VALUES (1, "Additional info", "EPAM", "Jun", "Past conference", "Short bio");
UPDATE user_info_seq SET next_val = 2 WHERE next_val = 1;

INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (1, "speaker@speaker.com", "Master", "Trybel", "CONFIRMED","speaker", 1);
UPDATE user_seq SET next_val = 2 WHERE next_val = 1;
INSERT INTO user_role VALUES (1, 1);

INSERT INTO talk VALUES (1, "Additional info", "Description", "2016-12-31 23:55:00", "title", 1, 1, 1, 1, 1, 1);
UPDATE talk_seq SET next_val = 2 WHERE next_val = 1;
INSERT INTO user_info_contact VALUES (1, "twitter.com", 2);
INSERT INTO user_info_contact VALUES (1, "facebook.com", 3);
INSERT INTO user_info_contact VALUES (1, "linkedin.com", 1);
INSERT INTO user_info_contact VALUES (1, "userblog.com", 4);

--
-- Setting default user with role SPEAKER with only registration fields
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio) VALUES (2, "", "", "");
UPDATE user_info_seq SET next_val = 3 WHERE next_val = 2;


INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (2, "user@gmail.com", "User", "User", "UNCONFIRMED", "password", 2);
UPDATE user_seq SET next_val = 3 WHERE next_val = 2;
INSERT INTO user_role VALUES (2, 1);

--
-- Setting default user with role ORGANIZER with only registration fields
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio)  VALUES (3, "", "", "");
UPDATE user_info_seq SET next_val = 4 WHERE next_val = 3;


INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (3, "organiser@gmail.com", "Organiser", "Organiser", "CONFIRMED","organiser", 3);
UPDATE user_seq SET next_val = 4 WHERE next_val = 3;
INSERT INTO user_role VALUES (3, 2);