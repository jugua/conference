--
-- Inserting necessary data into table `role`
--

INSERT INTO role VALUES (1, "SPEAKER"),(2, "ORGANISER");

UPDATE role_seq SET next_val = 3 WHERE next_val = 1;

--
-- Inserting necessary data into table `contact_type`
--

INSERT INTO contact_type VALUES(1, "LinkedIn"),(2, "Twitter"),(3, "FaceBook"),(4, "Blog");

UPDATE contact_type_seq SET next_val = 6 WHERE next_val = 1;
