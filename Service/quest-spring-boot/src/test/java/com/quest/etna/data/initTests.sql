INSERT INTO `user` (`id`, `username`, `password`, `role`, `is_active`, `creation_date`, `updated_date`) VALUES
(1, 'usernameAdmin', '$2a$10$eTBhNnvx3NB7jLvGfglzJu5mjGvYaZe8YaISIzN6NCxh.Z3CRgqfS', 'ROLE_ADMIN', 1, '2020-07-23 15:42:21.638000', '2020-07-23 15:42:21.638000'),
(2, 'usernameUser', '$2a$10$VRiFHgGNYFMhP8.2RKJ2eulFRmobnytqYrRjSJ05z4ZLBNzFsrW5q', 'ROLE_USER', 1, '2020-07-23 15:42:09.646000', '2020-07-23 15:42:09.646000');

INSERT INTO `board` (`id`, `title`, `creation_date`, `updated_date`) VALUES ('1', 'Existing Test Board', '2020-07-23 15:42:09.646000', '2020-07-23 15:42:09.646000');

INSERT INTO `issue` (`id`, `title`, `description`, `type`, `status`, `priority`, `board_id`, `milestone_id`, `reporter_id`, `assignee_id`, `creation_date`, `updated_date`)
VALUES ('1', 'Existing Test Issue', 'Description', 'BUG', 'OPEN', 'LOW', '1', NULL, '1', NULL, NULL, NULL);
INSERT INTO `milestone` (`id`, `creation_date`, `description`, `due_date`, `title`, `updated_date`, `board_id`)
VALUES (7, '2020-08-25 17:59:18.263000', 'test', '2020-08-25 17:59:18.263000', 'test', '2020-08-25 17:59:18.263000', 1);
