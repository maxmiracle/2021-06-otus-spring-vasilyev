INSERT INTO GENRE(ID, NAME) VALUES(1, 'Монография');

INSERT INTO AUTHOR(ID, NAME) VALUES(1, 'Frank Webb');
INSERT INTO BOOK(ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES(1, 'Dynamic Composition', 1, 1);

INSERT INTO AUTHOR(ID, NAME) VALUES(2, 'Василий Кандинский');
INSERT INTO BOOK(ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES(2, 'Точка и линия на плоскости', 2, 1);

INSERT INTO USER(ID, USERNAME, PASSWORD, ROLE) VALUES
(1, 'junior', '$2a$12$y3nlGQR9202kIT4dIMZNC.Jsmtgzk1ZdSO9MHVzECowGi5o7LEUFG', 'SOMEONE'),--pass=12345678
(2, 'manager', '$2a$12$0cznN5TT5fg2YRO3YJ3wgu/AtbCfS6IN3yWbAMNaA/Qz1qZMW2a9m', 'ADMIN'),--pass=5555
(3, 'user', '$2a$12$ac9gF7d6TRmFVgNarhpqfOjSoTZFb2C.cJYdBldf8Go8ngDNHUVkG', 'USER');--pass=user

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'junior'),
(2, 1, 'manager'),
(3, 1, 'user'),
(4, 0, 'ROLE_ADMIN'),
(5, 0, 'ROLE_USER'),
(6, 0, 'ROLE_SOMEONE');

INSERT INTO acl_class (id, class) VALUES
(1, 'org.maxvas.domain.Book'), --Используем только этот класс для security demo
(2, 'org.maxvas.domain.Author'),
(3, 'org.maxvas.domain.Genre'),
(4, 'org.maxvas.domain.User');


INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 2, 0), --Dynamic composition
(2, 1, 2, NULL, 2, 0); --Точка и линия


-- Mask
-- READ = 1
-- остальные не используются
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,  granting, audit_success, audit_failure) VALUES
                      (1,   1,                  1,          5,    1,    1, 1, 1),   --Грант USER for book1
                      (2,   2,                  2,          5,    1,    1, 1, 1),   --Грант USER for book2
                      (3,   1,                  3,          4,    1,    1, 1, 1),   --Грант ADMIN for book1
                      (4,   2,                  4,          4,    1,    1, 1, 1),   --Грант ADMIN for book2
                      (5,   1,                  5,          1,    1,    1, 1, 1);   --Грант junior read book1