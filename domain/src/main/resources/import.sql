--
-- Inserting necessary data into table `role`
--

INSERT INTO role VALUES (1, 'ROLE_SPEAKER'),(2, 'ROLE_ORGANISER'),(3, 'ROLE_ADMIN');

UPDATE role_seq SET next_val = 3 WHERE next_val = 1;

--
-- Inserting necessary data into table `contact_type`
--

INSERT INTO contact_type VALUES (1, 'LinkedIn'),(2, 'Twitter'),(3, 'FaceBook'),(4, 'Blog');

UPDATE contact_type_seq SET next_val = 6 WHERE next_val = 1;

--
-- Inserting necessary data into table `topic`
--

INSERT INTO topic VALUES (1, 'JVM Languages and new programming paradigms');
INSERT INTO topic VALUES (2, 'Web development and Java Enterprise technologies');
INSERT INTO topic VALUES (3, 'Software engineering practices');
INSERT INTO topic VALUES (4, 'Architecture & Cloud');
INSERT INTO topic VALUES (5, 'BigData & NoSQL');

UPDATE topic_seq SET next_val = 6 WHERE next_val = 1;

--
-- Inserting necessary data into table `type`
--

INSERT INTO `type` VALUES (1, 'Regular Talk');
INSERT INTO `type` VALUES (2, 'Lighting Talk');
INSERT INTO `type` VALUES (3, 'Online Talk');
INSERT INTO `type` VALUES (4, 'Hands-On-Lab');

UPDATE type_seq SET next_val = 5 WHERE next_val = 1;

--
-- Inserting necessary data into table `level`
--

INSERT INTO `level` VALUES (1, 'Beginner');
INSERT INTO `level` VALUES (2, 'Intermediate');
INSERT INTO `level` VALUES (3, 'Advanced');
INSERT INTO `level` VALUES (4, 'Expert');

UPDATE level_seq SET next_val = 5 WHERE next_val = 1;

--
-- Inserting necessary data into table `language`
--

INSERT INTO `language` VALUES (1, 'English'), (2, 'Ukrainian'), (3, 'Russian');

UPDATE language_seq SET next_val = 4 WHERE next_val = 3;

--
-- Setting default user with role SPEAKER with all fields
--

INSERT INTO user_info VALUES (1, 'Additional info', 'EPAM', 'Jun', 'Past conference', 'Short bio');
UPDATE user_info_seq SET next_val = 2 WHERE next_val = 1;

INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (1, 'speaker@speaker.com', 'Master', 'Trybel', 'CONFIRMED','$2a$10$OLtqxD4e0UWymWn7MF7x3e6RVX5RxaDxXGT1FjmTbUkdgyZY3.EKG', 1);
UPDATE user_seq SET next_val = 2 WHERE next_val = 1;
INSERT INTO user_role VALUES (1, 1);


INSERT INTO talk VALUES (1, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:51:00', 'talk #1', 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (2, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:52:00', 'talk #2', 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (3, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:53:00', 'talk #3', 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (4, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:54:00', 'talk #4', 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (5, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:55:00', 'talk #5', 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (6, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:56:00', 'talk #6', 1, 1, null, 4, 1, 1);

UPDATE talk_seq SET next_val = 7 WHERE next_val = 1;
INSERT INTO user_info_contact VALUES (1, 'twitter.com', 2);
INSERT INTO user_info_contact VALUES (1, 'facebook.com', 3);
INSERT INTO user_info_contact VALUES (1, 'linkedin.com', 1);
INSERT INTO user_info_contact VALUES (1, 'userblog.com', 4);

--
-- Setting default user with role SPEAKER with only registration fields
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio) VALUES (2, '', '', '');
UPDATE user_info_seq SET next_val = 3 WHERE next_val = 2;


INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (2, 'user@gmail.com', 'User', 'User', 'UNCONFIRMED', '$2a$10$j6MlhWXyU.oSZPZxSwEO3.zHfU9vwsl4Fg1F8nsDR8c9EkOIHXl2O', 2);
UPDATE user_seq SET next_val = 3 WHERE next_val = 2;
INSERT INTO user_role VALUES (2, 1);

--
-- Setting default user with role ORGANIZER with only registration fields
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio)  VALUES (3, '', '', '');
UPDATE user_info_seq SET next_val = 4 WHERE next_val = 3;


INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (3, 'organiser@gmail.com', 'Organiser', 'Organiser', 'CONFIRMED','$2a$10$C08knHEu64PED9wU1lKtMeSNELBwOqP0q59r9I/inBVcWWdLI9BPC', 3);
UPDATE user_seq SET next_val = 4 WHERE next_val = 3;
INSERT INTO user_role VALUES (3, 2);

--
-- Setting default user with role ORGANIZER with only registration fields
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio)  VALUES (4, '', '', '');
UPDATE user_info_seq SET next_val = 5 WHERE next_val = 4;


INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (4, 'organiser2@gmail.com', 'Organiser2', 'Organiser2', 'CONFIRMED','$2a$10$C08knHEu64PED9wU1lKtMeSNELBwOqP0q59r9I/inBVcWWdLI9BPC', 4);
UPDATE user_seq SET next_val = 5 WHERE next_val = 4;
INSERT INTO user_role VALUES (4, 2);

--
-- Insert organiser-admin
--

INSERT INTO user_info (user_info_id, company, job_title, short_bio)  VALUES (5, '', '', '');
UPDATE user_info_seq SET next_val = 6 WHERE next_val = 5;

INSERT INTO `user`  (user_id, email, first_name, last_name, status, password, user_info_id) VALUES (5, 'admin@gmail.com', 'I''m super', 'Admin', 'CONFIRMED','$2a$10$ASFKX9KVHmSEShdBFpCskORriCNRMUYGMy7y7PSRuPhaV5hHSaBU.', 5);
UPDATE user_seq SET next_val = 6 WHERE next_val = 5;
INSERT INTO user_role VALUES (5, 2);
INSERT INTO user_role VALUES (5, 3);

