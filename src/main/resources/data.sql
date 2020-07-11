INSERT INTO user (admin, name, password, profile_photo) VALUES (false, "Leo","123","photo.com/Leo.jpg");
INSERT INTO user (admin, name, password, profile_photo) VALUES (false, "Mineda","123","photo.com/Mineda.jpg");
INSERT INTO user (admin, name, password, profile_photo) VALUES (false, "Sakaue","123","photo.com/Sakaue.jpg");

INSERT INTO post (picture_link, author_id) VALUES ("photo.com/Mineda/post1.jpg", 2);
INSERT INTO post (picture_link, author_id) VALUES ("photo.com/Mineda/post2.jpg", 2);
INSERT INTO post (picture_link, author_id) VALUES ("photo.com/Sakaue/post1.jpg", 3);
INSERT INTO post (picture_link, author_id) VALUES ("photo.com/Sakaue/post2.jpg", 3);
INSERT INTO post (picture_link, author_id) VALUES ("photo.com/Sakaue/post3.jpg", 3);

INSERT INTO user_follower (usr_id_following, usr_id_followed) VALUES (1, 2);
INSERT INTO user_follower (usr_id_following, usr_id_followed) VALUES (1, 3);
