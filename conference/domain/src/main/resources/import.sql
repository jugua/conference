--
-- Inserting necessary data into table `role`
--

INSERT INTO role VALUES (1, 'ROLE_SPEAKER');
INSERT INTO role VALUES (2, 'ROLE_ORGANISER');
INSERT INTO role VALUES (3, 'ROLE_ADMIN');
INSERT INTO role VALUES (4, 'ROLE_USER');

--
-- Inserting necessary data into table `contact_type`
--

INSERT INTO contact_type VALUES (1, 'LinkedIn');
INSERT INTO contact_type VALUES (2, 'Twitter');
INSERT INTO contact_type VALUES (3, 'Facebook');
INSERT INTO contact_type VALUES (4, 'Blog');

--
-- Inserting necessary data into table `topic`
--

INSERT INTO topic VALUES (1, 'JVM Languages and new programming paradigms');
INSERT INTO topic VALUES (2, 'Web development and Java Enterprise technologies');
INSERT INTO topic VALUES (3, 'Software engineering practices');
INSERT INTO topic VALUES (4, 'Architecture & Cloud');
INSERT INTO topic VALUES (5, 'BigData & NoSQL');

--
-- Inserting necessary data into table `type`
--

INSERT INTO `type` VALUES (1, 'Regular Talk');
INSERT INTO `type` VALUES (2, 'Lighting Talk');
INSERT INTO `type` VALUES (3, 'Online Talk');
INSERT INTO `type` VALUES (4, 'Hands-On-Lab');

--
-- Inserting necessary data into table `level`
--

INSERT INTO `level` VALUES (1, 'Beginner');
INSERT INTO `level` VALUES (2, 'Intermediate');
INSERT INTO `level` VALUES (3, 'Advanced');
INSERT INTO `level` VALUES (4, 'Expert');

--
-- Inserting necessary data into table `language`
--

INSERT INTO `language` VALUES (1, 'English'), (2, 'Ukrainian'), (3, 'Russian');

--
-- Setting default user with role SPEAKER with all fields
--

INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `status`) VALUES (1, 'speaker@speaker.com', 'Master', 'Trybel', '$2a$10$OLtqxD4e0UWymWn7MF7x3e6RVX5RxaDxXGT1FjmTbUkdgyZY3.EKG', 'CONFIRMED');


INSERT INTO user_info VALUES (1, 'Additional info', 'EPAM', 'Jun', 'Past conference', 'Short bio',1);


INSERT INTO user_role VALUES (1, 1);

INSERT INTO contact VALUES (1, 1, 'linkedin.com', 1);
INSERT INTO contact VALUES (2, 1, 'twitter.com', 2);
INSERT INTO contact VALUES (3, 1, 'facebook.com', 3);
INSERT INTO contact VALUES (4, 1, 'userblog.com', 4);

--
-- Setting default user with role SPEAKER with only registration fields
--

INSERT INTO `user`  (`id`, email, first_name, last_name, status, password) VALUES (2, 'user@gmail.com', 'User', 'User', 'UNCONFIRMED', '$2a$10$j6MlhWXyU.oSZPZxSwEO3.zHfU9vwsl4Fg1F8nsDR8c9EkOIHXl2O');


INSERT INTO user_info (`id`, company, job_title, short_bio, user_id) VALUES (2, '', '', '',2);


INSERT INTO user_role VALUES (2, 1);

--
-- Setting default user with role ORGANIZER with only registration fields
--

INSERT INTO `user`  (`id`, email, first_name, last_name, status, password) VALUES (3, 'organiser@gmail.com', 'Organiser', 'Organiser', 'CONFIRMED','$2a$10$C08knHEu64PED9wU1lKtMeSNELBwOqP0q59r9I/inBVcWWdLI9BPC');


INSERT INTO user_info (`id`, company, job_title, short_bio, user_id)  VALUES (3, '', '', '',3);


INSERT INTO user_role VALUES (3, 2);

--
-- Setting default user with role ORGANIZER with only registration fields
--

INSERT INTO `user`  (`id`, email, first_name, last_name, status, password) VALUES (4, 'organiser2@gmail.com', 'Organiser2', 'Organiser2', 'CONFIRMED','$2a$10$C08knHEu64PED9wU1lKtMeSNELBwOqP0q59r9I/inBVcWWdLI9BPC');


INSERT INTO user_info (`id`, company, job_title, short_bio, user_id)  VALUES (4, '', '', '', 4);

INSERT INTO user_role VALUES (4, 2);

--
-- Insert organiser-admin
--

INSERT INTO `user`  (`id`, email, first_name, last_name, status, password) VALUES (5, 'admin@gmail.com', 'I''m super', 'Admin', 'CONFIRMED','$2a$10$ASFKX9KVHmSEShdBFpCskORriCNRMUYGMy7y7PSRuPhaV5hHSaBU.');


INSERT INTO user_info (`id`, company, job_title, short_bio, user_id)  VALUES (5, '', '', '', 5);

INSERT INTO user_role VALUES (5, 3);

--
-- Setting default user with role USER with only registration fields
--

INSERT INTO `user`  (`id`, email, first_name, last_name, status, password) VALUES (6, 'simple@gmail.com', 'simpleuser', 'simpleuser', 'CONFIRMED','$2a$10$ASFKX9KVHmSEShdBFpCskORriCNRMUYGMy7y7PSRuPhaV5hHSaBU.');

INSERT INTO user_info (`id`, company, job_title, short_bio, user_id)  VALUES (6, '', '', '',6);

INSERT INTO user_role VALUES (6, 4);

INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (1, 'JavaDay 1', 'The Very First JavaDay', 'Kiev', '2018-09-15', '2018-09-25', '2018-08-04', '2018-08-22', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (2, 'JavaDay 2', 'Second JavaDay', 'Boston', '2018-11-23', '2018-11-30', '2018-11-01', '2018-11-01', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (3, 'JavaDay 3', 'Third JavaDay', 'New York', '2018-12-04', '2018-12-10', '2018-12-04', '2018-12-10', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (5, 'JavaDay 5', 'Second JavaDay', 'London', '2018-08-22', '2018-08-30', '2018-07-10', '2018-07-25', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (6, 'JavaDay 6', 'Third JavaDay', 'Beijin', '2018-03-25', '2018-04-10', '2018-02-22', '2018-03-15', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (7, 'JavaDay 7', 'Third JavaDay', 'Tel Aviv', '2018-02-15', '2018-02-25', '2018-02-04', '2018-03-27', 'nologo');
INSERT INTO `conference` (`id`, title, description, location, start_date, end_date, call_for_paper_start_date, call_for_paper_end_date, path_to_logo) VALUES (8, 'JavaDay 8', 'Very First JavaDay', 'Beirut', '2018-03-15', '2018-05-03', '2018-03-20', '2018-06-05', 'nologo');

INSERT INTO talk VALUES (1, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:51:00', 'talk #1', 1, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (2, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:52:00', 'talk #2', 2, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (3, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:53:00', 'talk #3', 2, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (4, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:54:00', 'talk #4', 3, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (5, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:55:00', 'talk #5', 3, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (6, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:16:00', 'talk #6', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (7, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:36:00', 'talk #7', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (8, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:26:00', 'talk #8', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (9, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:56:00', 'talk #9', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (10, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:56:00', 'talk #10', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (11, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 22:56:00', 'talk #11', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (12, 'Additional info', 'Description','',null,'REJECTED', '2015-12-31 23:51:07', 'talk #12', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (13, 'Additional info', 'Description','',null,'REJECTED', '2016-10-31 23:32:00', 'talk #13', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (14, 'Additional info', 'Description','',null,'NEW', '2016-01-31 23:54:13', 'talk #14', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (15, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:12:17', 'talk #15', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (16, 'Additional info', 'Description','',null,'NEW', '2016-12-04 23:51:00', 'talk #16', 6, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (17, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-09-17 11:52:00', 'talk #17', 7, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (18, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:11:14', 'talk #18', 8, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (19, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:23:23', 'talk #19', 8, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (20, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:47:34', 'talk #20', 7, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (21, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:38:21', 'talk #21', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (22, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:22:55', 'talk #22', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (23, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:11:22', 'talk #23', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (24, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:22:22', 'talk #24', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (25, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:33:33', 'talk #25', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (26, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 22:56:55', 'talk #26', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (27, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:51:44', 'talk #27', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (28, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:59:33', 'talk #28', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (29, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:54:22', 'talk #29', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (30, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:58:11', 'talk #30', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (31, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:51:50', 'talk #31', 5, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (32, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:52:40', 'talk #32', 6, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (33, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:53:30', 'talk #33', 7, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (34, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:54:20', 'talk #34', 8, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (35, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:55:10', 'talk #35', 8, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (36, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:50:00', 'talk #36', 7, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (37, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:49:00', 'talk #37', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (38, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:48:00', 'talk #38', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (39, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:47:00', 'talk #39', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (40, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:46:00', 'talk #40', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (41, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 22:45:00', 'talk #41', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (42, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:44:00', 'talk #42', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (43, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:43:00', 'talk #43', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (44, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:42:00', 'talk #44', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (45, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:41:00', 'talk #45', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (46, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:40:00', 'talk #46', 5, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (47, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:39:00', 'talk #47', 6, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (48, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:38:00', 'talk #48', 7, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (49, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:37:00', 'talk #49', 8, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (50, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:36:19', 'talk #50', 8, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (51, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:35:00', 'talk #51', 7, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (52, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:34:00', 'talk #52', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (53, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:33:00', 'talk #53', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (54, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:32:00', 'talk #54', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (55, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:31:00', 'talk #55', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (56, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 22:30:00', 'talk #56', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (57, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:29:00', 'talk #57', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (58, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:28:00', 'talk #58', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (59, 'Additional info', 'Description','',null,'NEW', '2015-12-05 23:20:02', 'talk #59', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (60, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:26:03', 'talk #60', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (61, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:25:00', 'talk #61', 6, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (62, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:24:00', 'talk #62', 7, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (63, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:23:00', 'talk #63', 8, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (64, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:22:00', 'talk #64', 8, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (65, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:21:00', 'talk #65', 7, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (66, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:20:00', 'talk #66', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (67, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:19:00', 'talk #67', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (68, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:18:00', 'talk #68', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (69, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:17:00', 'talk #69', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (70, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:16:00', 'talk #70', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (71, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 22:15:00', 'talk #71', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (72, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:14:00', 'talk #72', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (73, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:13:00', 'talk #73', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (74, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:12:00', 'talk #74', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (75, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:11:00', 'talk #75', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (76, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:10:00', 'talk #76', 7, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (77, 'Additional info', 'Description','',null,'IN_PROGRESS', '2015-09-21 02:32:18', 'talk #77', 8, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (78, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:08:00', 'talk #78', 8, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (79, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 23:07:00', 'talk #79', 7, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (80, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 23:06:00', 'talk #80', 6, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (81, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:05:00', 'talk #81', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (82, 'Additional info', 'Description','',null,'NEW', '2016-12-31 23:04:00', 'talk #82', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (83, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 23:03:00', 'talk #83', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (84, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 20:02:00', 'talk #84', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (85, 'Additional info', 'Description','',null,'NEW', '2016-12-31 21:01:00', 'talk #85', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (86, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 19:00:00', 'talk #86', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (87, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 19:51:00', 'talk #87', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (88, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 18:59:00', 'talk #88', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (89, 'Additional info', 'Description','',null,'NEW', '2016-12-31 17:54:00', 'talk #89', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (90, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 16:58:00', 'talk #90', 7, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (91, 'Additional info', 'Description','',null,'NEW', '2016-12-31 15:51:00', 'talk #91', 8, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (92, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 14:52:00', 'talk #92', 8, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (93, 'Additional info', 'Description','',null,'NEW', '2016-12-31 13:53:00', 'talk #93', 7, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (94, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 12:54:00', 'talk #94', 6, 1, 1, null, 2, 1, 1);
INSERT INTO talk VALUES (95, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 11:55:00', 'talk #95', 5, 1, 1, null, 3, 1, 1);
INSERT INTO talk VALUES (96, 'Additional info', 'Description','',null,'NEW', '2016-12-31 10:16:00', 'talk #96', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (97, 'Additional info', 'Description','',null,'NEW', '2016-12-31 09:36:00', 'talk #97', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (98, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 08:26:00', 'talk #98', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (99, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 07:56:00', 'talk #99', 1, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (100, 'Additional info', 'Description','',null,'NEW', '2016-12-31 06:56:00', 'talk #100', 2, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (101, 'Additional info', 'Description','',null,'IN_PROGRESS', '2016-12-31 05:56:00', 'talk #101', 3, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (102, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 04:51:00', 'talk #102', 5, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (103, 'Additional info', 'Description','',null,'REJECTED', '2016-12-31 03:59:00', 'talk #103', 6, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (104, 'Additional info', 'Description','',null,'NEW', '2016-12-31 02:54:00', 'talk #104', 7, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (105, 'Additional info', 'Description','',null,'APPROVED', '2016-12-31 01:58:00', 'talk #105', 8, 1, 1, null, 4, 1, 1);
INSERT INTO talk VALUES (106, 'Additional info', 'Description','',null,'NEW', '2017-10-11 23:51:00', 'talk #106', 8, 1, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (107, 'Additional info', 'Description','',null,'IN_PROGRESS', '2017-10-11 23:52:00', 'talk #107', 1, 2, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (108, 'Additional info', 'Description','',null,'NEW', '2017-10-10 23:53:00', 'talk #108', 2, 3, 1, null, 1, 1, 1);
INSERT INTO talk VALUES (109, 'Additional info', 'Description','',null,'REJECTED', '2017-10-30 23:54:00', 'talk #109', 3, 1, 1, null, 2, 1, 1);

INSERT INTO `conference_organiser` (`conference_id`, `organiser_id`) VALUES (1, 4);
INSERT INTO `conference_organiser` (`conference_id`, `organiser_id`) VALUES (1, 1);
INSERT INTO `conference_organiser` (`conference_id`, `organiser_id`) VALUES (1, 1);