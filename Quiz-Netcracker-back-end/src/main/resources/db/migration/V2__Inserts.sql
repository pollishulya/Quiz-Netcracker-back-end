-- Заполняем уровни сложности
INSERT INTO levels (id, description, title) VALUES ('fdecef96-6ee3-4d25-803b-26042cb532a7', 'Easy', 'Easy');
INSERT INTO levels (id, description, title) VALUES ('0458cc5b-8871-4807-98e8-68451f3b1f0b', 'Middle', 'Middle');
INSERT INTO levels (id, description, title) VALUES ('d401d941-6883-45c5-9038-4bbd9f63bb3c', 'High', 'High');

-- Инициализируем админа
INSERT INTO users (id, activation_code, active, login, mail, password, role)
VALUES ('365924ba-fe34-412d-99b6-dec9554815d9', NULL, TRUE, 'Administrator', 'admin@mail.ru', '$2a$10$xlS02xL1DHt3gwWCgjTI2.UVNU58EjZkgVYnyS.Y.LFY/tRyMC/Ae', 'ADMIN');

-- Заполняем категории
INSERT INTO categories(id, description, name) VALUES ('f7b926e4-3a0c-43d5-913d-28e27a612a76', 'Наука', 'Наука');
INSERT INTO categories(id, description, name) VALUES ('5fb660e7-5425-46da-9b33-6a8fc4c716a0', 'Культура', 'Культура');
INSERT INTO categories(id, description, name) VALUES ('ebf7b015-6061-43d1-92e7-3fac5c5802b3', 'Общее', 'Общее');
INSERT INTO categories(id, description, name) VALUES ('634572e8-6926-463d-8712-c699b4821cdc', 'Разное', 'Разное');
INSERT INTO categories(id, description, name) VALUES ('ed2b4a73-a6ec-476a-b82e-16eac9249f5e', 'Мультфильмы', 'Мультфильмы');
INSERT INTO categories(id, description, name) VALUES ('ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', 'Спорт', 'Спорт');

-- Разрабатываем игру про футбол (запустить может только админ)
INSERT INTO games(id, description, photo, name, player_id)
VALUES ('716fb029-148d-4515-8aca-7cb8a604ee24', 'Общие факты о футболе', 'https://quiz-netcracker-basket.s3.eu-north-1.amazonaws.com/1611788079133-myach-futbolnyj-myach-adidas-finale-18-top-training-ball-cw4134.jpg', 'Викторина про футбол', NULL);

INSERT INTO players(id, email, name, photo, user_id, game_room_id)
VALUES ('fe51e1e3-60e5-4c4b-800f-1ede7d7eb6dd', 'admin@mail.ru', 'Administrator', NULL, '365924ba-fe34-412d-99b6-dec9554815d9', NULL);

UPDATE games SET player_id = 'fe51e1e3-60e5-4c4b-800f-1ede7d7eb6dd' WHERE id = '716fb029-148d-4515-8aca-7cb8a604ee24';

INSERT INTO game_room(id, game_id) VALUES ('c3856e57-89f9-47c8-b178-92649a38b671', '716fb029-148d-4515-8aca-7cb8a604ee24');

UPDATE players SET game_room_id = 'c3856e57-89f9-47c8-b178-92649a38b671' WHERE id = 'fe51e1e3-60e5-4c4b-800f-1ede7d7eb6dd';

-- 1 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('9885d35d-4b8f-4b19-8e10-10e15691c0d5', 'В какой стране и в каком городе проходил Финал Клубного чемпионата мира 2019?', 'В какой стране, и в каком городе проходил Финал Клубного чемпионата мира 2019?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES ('9885d35d-4b8f-4b19-8e10-10e15691c0d5', TRUE, 'Доха (Катар)', '9885d35d-4b8f-4b19-8e10-10e15691c0d5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES ('f69d909d-d909-4212-ae46-dcf1f9148939', FALSE , 'Абу-Даби (ОАЭ)', '9885d35d-4b8f-4b19-8e10-10e15691c0d5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES ('cc166654-4689-4947-9879-abc7d92122b9', FALSE, 'Лондон (Англия)', '9885d35d-4b8f-4b19-8e10-10e15691c0d5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES ('ff4a4eed-b69f-4451-98eb-0cd49be383ed', FALSE, 'Иокогама (Япония)', '9885d35d-4b8f-4b19-8e10-10e15691c0d5');

-- 2 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('f481f984-ee1c-45f7-83e9-4aa28c2f5e9a', 'Кто стал победителем Клубного чемпионата мира 2019?', 'Кто стал победителем Клубного чемпионата мира 2019?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1defa588-d865-41dd-9c63-a4bef4547727', FALSE, 'Фламенго (Бразилия)', 'f481f984-ee1c-45f7-83e9-4aa28c2f5e9a');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('15eef00b-8f52-4280-90d8-4559afa15dd5', FALSE, 'Манчестер Юнайтед (Англия)', 'f481f984-ee1c-45f7-83e9-4aa28c2f5e9a');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('ca7614c1-99a1-42c5-9315-300ef740664a', FALSE, 'Бавария (Германия)', 'f481f984-ee1c-45f7-83e9-4aa28c2f5e9a');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('65d6c565-246e-4a9b-8917-83a00a8a2a4e', TRUE, 'Ливерпуль (Англия)', 'f481f984-ee1c-45f7-83e9-4aa28c2f5e9a');

-- 3 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('b2022474-3a82-4949-a28e-49b72eeaac11', 'Кто признан лучшим вратарём вы 2019 году по версии ФИФА?', 'Кто признан лучшим вратарём вы 2019 году по версии ФИФА?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('3729ad4a-2aba-43c7-8cf6-20079191294e', FALSE, 'Ян Облак из «Атлетико»', 'b2022474-3a82-4949-a28e-49b72eeaac11');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('e7e2c7e6-f5f0-4847-a997-dde2a5934a88', TRUE, 'Алиссон из «Ливерпуля»', 'b2022474-3a82-4949-a28e-49b72eeaac11');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('22434073-6785-4a62-93f0-f9a07f8fca69', FALSE, 'Тибо Куртуа из «Реала»', 'b2022474-3a82-4949-a28e-49b72eeaac11');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('8035bdff-a8f0-4e54-bb0b-3e0e6382d95c', FALSE, 'Давид де Хеа из «Манчестер Юнайтед»', 'b2022474-3a82-4949-a28e-49b72eeaac11');

-- 4 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('06ad3516-43bf-4cdc-a0f7-9a6139e84e56', 'Кто является лучшим бомбардиром за всю историю сборной Португалии?', 'Кто является лучшим бомбардиром за всю историю сборной Португалии?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('7f61672d-1210-40c4-891e-2c812f7aa2d1', TRUE, 'Криштиану Роналду', '06ad3516-43bf-4cdc-a0f7-9a6139e84e56');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('01400df8-85cb-414c-9a2a-4b3d0088468a', FALSE, 'Маттейс де Лигт', '06ad3516-43bf-4cdc-a0f7-9a6139e84e56');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('be984e5c-2d41-45be-913b-5d43fb74067e', FALSE, 'Вирджил ван Дейк', '06ad3516-43bf-4cdc-a0f7-9a6139e84e56');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('eee9e447-1585-4066-86fb-0a3720a62c76', FALSE, 'Лионель Месси', '06ad3516-43bf-4cdc-a0f7-9a6139e84e56');

-- 5 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('ddab8485-db15-4197-8b2f-2b587cb99ce2', 'В какой стране появился и начал развиваться футбол?', 'В какой стране появился и начал развиваться футбол?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('ef818a72-6680-4d8f-a760-623b78617a19', FALSE, 'Бразилия', 'ddab8485-db15-4197-8b2f-2b587cb99ce2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('77ce25e3-9013-4bec-aa80-d226e9de9b9f', FALSE, 'Испания', 'ddab8485-db15-4197-8b2f-2b587cb99ce2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('065645cf-e876-474a-b084-ee3596dd1de8', TRUE, 'Англия', 'ddab8485-db15-4197-8b2f-2b587cb99ce2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('93fc1932-0449-45a6-a1ba-e7366210de8b', FALSE, 'Португалия', 'ddab8485-db15-4197-8b2f-2b587cb99ce2');

-- 6 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('d7ffbf1c-f858-469a-863a-68973810c158', 'В какой стране впервые проходил Кубок мира ФИФА?', 'В какой стране впервые проходил Кубок мира ФИФА?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('a55e1d80-f799-4568-9a2d-6b8be5487b14', FALSE, 'Франция', 'd7ffbf1c-f858-469a-863a-68973810c158');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('4df49b2a-03f9-41f5-bc01-8acc5b955148', TRUE, 'Уругвай', 'd7ffbf1c-f858-469a-863a-68973810c158');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('62cdd3e5-96df-40e3-a3e5-1431f81fbf71', FALSE, 'Англия', 'd7ffbf1c-f858-469a-863a-68973810c158');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('15f3d195-9fd9-4c21-b27b-900b210f3127', FALSE, 'Испания', 'd7ffbf1c-f858-469a-863a-68973810c158');

-- 7 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('6513ed2e-7b13-474b-bab7-f4c28a008f4e', 'Как называется гол, который футболист забил в свои ворота?', 'Как называется гол, который футболист забил в свои ворота?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('61d5ce9e-928f-4706-8e29-e8c3df2dfe8c', TRUE, 'Автогол', '6513ed2e-7b13-474b-bab7-f4c28a008f4e');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('51011eaa-643c-42df-a948-40d1618287ca', FALSE, 'Штрафной', '6513ed2e-7b13-474b-bab7-f4c28a008f4e');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('924d4642-f355-4c97-ac1d-120b98e601b7', FALSE, '«Мертвый мяч»', '6513ed2e-7b13-474b-bab7-f4c28a008f4e');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('09bea017-b5da-4077-ac5c-9d714a0b9def', FALSE, 'Ложный маневр', '6513ed2e-7b13-474b-bab7-f4c28a008f4e');

-- 8 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('8a462607-7867-4f5f-8ffa-5cbd3f9ba185', 'Какое количество игроков одновременно находится на игровом поле с двух сторон?', 'Какое количество игроков одновременно находится на игровом поле с двух сторон?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(ID, ANSWER_IS_RIGHT, TITLE, QUESTION_ID)
VALUES('13170e63-6807-4499-bcdb-a48df44b25d1', FALSE, '11 игроков', '8a462607-7867-4f5f-8ffa-5cbd3f9ba185');

INSERT INTO answers(ID, ANSWER_IS_RIGHT, TITLE, QUESTION_ID)
VALUES('fa5c6f72-b2c6-41d0-aedd-7609dcfec279', FALSE, '30 игроков', '8a462607-7867-4f5f-8ffa-5cbd3f9ba185');

INSERT INTO answers(ID, ANSWER_IS_RIGHT, TITLE, QUESTION_ID)
VALUES('d96c793c-5c58-4cc4-8976-2789b28a385b', TRUE, '22 игрока', '8a462607-7867-4f5f-8ffa-5cbd3f9ba185');

INSERT INTO answers(ID, ANSWER_IS_RIGHT, TITLE, QUESTION_ID)
VALUES('548344e7-4913-45af-a3a8-8b37c79b573c', FALSE, '24 игрока', '8a462607-7867-4f5f-8ffa-5cbd3f9ba185');

-- 9 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('7d40166b-d70a-4a24-b2f0-3457825ffe05', 'Команда из какой страны чаще всего становилась победителем чемпионата мира и сколько раз?', 'Команда из какой страны чаще всего становилась победителем чемпионата мира и сколько раз?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('41a42bf3-538f-4ccf-bddd-16809949d365', FALSE, 'Англия (4 победы)', '7d40166b-d70a-4a24-b2f0-3457825ffe05');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('222fbc43-ef7d-45b7-a8d6-244713bf144a', FALSE, 'Германия (3 победы)', '7d40166b-d70a-4a24-b2f0-3457825ffe05');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('9ac60285-4ea5-49b1-a623-0d7becd666d8', FALSE, 'Испания (4 победы)', '7d40166b-d70a-4a24-b2f0-3457825ffe05');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('b9d9280b-6d14-4b24-aa89-78fb87eb0011', TRUE, 'Бразилия (5 побед)', '7d40166b-d70a-4a24-b2f0-3457825ffe05');

-- 10 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('e8afd1cd-1a7c-4c0a-a43e-7be06026dbc6', 'Назовите правильные размеры стандартных футбольных ворот?', 'Назовите правильные размеры стандартных футбольных ворот?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('14410ad4-3498-4646-b62a-7cb90c14e258', FALSE, '7,22 на 2,45 метра', 'e8afd1cd-1a7c-4c0a-a43e-7be06026dbc6');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('2d42f3ad-0bfe-435f-93f3-6e55520339d0', TRUE, '7,32 на 2,44 метра', 'e8afd1cd-1a7c-4c0a-a43e-7be06026dbc6');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('953a0ab4-a521-4c0b-825e-a6ba67994246', FALSE, '6,9 на 2,11 метра', 'e8afd1cd-1a7c-4c0a-a43e-7be06026dbc6');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('09e85e75-4d3a-4be3-b377-9340f5c2ed72', FALSE, '7,11 на 2,33 метра', 'e8afd1cd-1a7c-4c0a-a43e-7be06026dbc6');

-- 11 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('e0fb4813-d76c-4689-b3cf-5ba8cb3fded4', 'Как правильно расшифровывается аббревиатура УЕФА?', 'Как правильно расшифровывается аббревиатура УЕФА?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('f6b033ff-9e1f-43ef-8d85-fa7798cbd3b6', TRUE, 'Союз европейских футбольных ассоциаций', 'e0fb4813-d76c-4689-b3cf-5ba8cb3fded4');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('af62e791-7a32-440e-8cca-718298855458', FALSE, 'Союз евроинтегрированных футбольных ассоциаций', 'e0fb4813-d76c-4689-b3cf-5ba8cb3fded4');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('dbd61fd0-9683-4464-a11d-5b651ff0fee8', FALSE, 'Содружество европейских футбольных абсорбций', 'e0fb4813-d76c-4689-b3cf-5ba8cb3fded4');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('0e784cfa-328f-4814-85d7-3b671b0b35f6', FALSE, 'Собрание европейских футбольных ассоциаций', 'e0fb4813-d76c-4689-b3cf-5ba8cb3fded4');

-- 12 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('f459cd9a-69cc-4179-a25d-19f5f5f4d1f7', 'Что оформил игрок, забивший 3 мяча за один матч?', 'Что оформил игрок, забивший 3 мяча за один матч?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('e14e8f88-e079-4bbc-af7d-fe88df1f5346', FALSE, 'Автогол', 'f459cd9a-69cc-4179-a25d-19f5f5f4d1f7');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('10af100f-eb69-40c3-a708-9b51f2a0afc0', FALSE, 'Гандикап', 'f459cd9a-69cc-4179-a25d-19f5f5f4d1f7');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('7a64bc65-e65f-4015-9a9e-b5b18c6bdc7b', FALSE, 'Трипл-сек', 'f459cd9a-69cc-4179-a25d-19f5f5f4d1f7');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('b7e629f2-7b47-41c2-a62e-b390a03b1aa1', TRUE, 'Хет-трик', 'f459cd9a-69cc-4179-a25d-19f5f5f4d1f7');

-- 13 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('ce16ebbd-33f7-4037-93df-6d5193c09dc3', 'Сколько существует размеров футбольных мячей?', 'Сколько существует размеров футбольных мячей?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('a9014df4-b491-411f-aa8b-23b4c9ba8abc', FALSE, '2 размера', 'ce16ebbd-33f7-4037-93df-6d5193c09dc3');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1b3baaf3-d870-4550-b095-365334f04771', TRUE, '5 размеров', 'ce16ebbd-33f7-4037-93df-6d5193c09dc3');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('6f341298-570f-47f2-8f0d-31ccf05172c9', FALSE, '3 размера', 'ce16ebbd-33f7-4037-93df-6d5193c09dc3');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('959373f7-337e-483b-9bf2-776081b3d67b', FALSE, '6 размеров', 'ce16ebbd-33f7-4037-93df-6d5193c09dc3');

-- 14 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('57828661-118f-456f-a1d2-6a6497c8e46f', 'Как называется самая почетная персональная награда для футболиста?', 'Как называется самая почетная персональная награда для футболиста?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('120deb1a-2f45-4f16-8415-6b932e4f345f', TRUE, 'Золотой мяч', '57828661-118f-456f-a1d2-6a6497c8e46f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('ef809494-42f9-4145-bfc7-0b8b05d81b17', FALSE, 'Статуэтка чемпиона', '57828661-118f-456f-a1d2-6a6497c8e46f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('8db3ba4d-c88d-4911-bf81-5e70a85d3672', FALSE, 'Кубок чемпиона', '57828661-118f-456f-a1d2-6a6497c8e46f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('d14ff442-8c2d-489f-b22c-69998589df2a', FALSE, 'Золотая бутса', '57828661-118f-456f-a1d2-6a6497c8e46f');

-- 15 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('02aa08a8-fa73-47a7-98bd-88da0d9f28fb', 'Футбольный клуб какой страны в 20 веке 5 раз подряд становился победителем Лиги Чемпионов? Как он называется?', 'Футбольный клуб какой страны в 20 веке 5 раз подряд становился победителем Лиги Чемпионов? Как он называется?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('8f3579ee-be0a-4a0a-8ead-2d137a56dea2', FALSE, 'Арсенал (Англия)', '02aa08a8-fa73-47a7-98bd-88da0d9f28fb');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('f884aaba-4b7d-45a1-babc-d6c799ef414d', TRUE, 'Реал Мадрид (Испания)', '02aa08a8-fa73-47a7-98bd-88da0d9f28fb');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('e7c9b067-4034-4a6f-a9b2-01ab29ce4383', FALSE, 'Ювентус (Италия)', '02aa08a8-fa73-47a7-98bd-88da0d9f28fb');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('f7801bd9-c798-4b76-99ed-79d4bcd8b9ae', FALSE, 'Манчестер Сити (Англия)', '02aa08a8-fa73-47a7-98bd-88da0d9f28fb');

-- 16 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('d8ee072d-de99-4a2a-af48-b5ce3efc7145', 'Что такое финт в футболе?', 'Что такое финт в футболе?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('a3f15c01-72f4-4baa-85e6-9b9dbd234b87', FALSE, 'Название тайма', 'd8ee072d-de99-4a2a-af48-b5ce3efc7145');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('5e4c110d-cc46-4e48-812f-b13ba2c572ea', FALSE, 'Определение местоположения мяча', 'd8ee072d-de99-4a2a-af48-b5ce3efc7145');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('46ceda35-c442-4f97-9efb-0819ffbd0476', TRUE, 'Технический элемент, который выполняют, чтобы обмануть соперника', 'd8ee072d-de99-4a2a-af48-b5ce3efc7145');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('34c5a7a9-feb1-4e22-8406-f766ab38fb69', FALSE, 'Второе наименование полузащитника', 'd8ee072d-de99-4a2a-af48-b5ce3efc7145');

-- 17 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('e6a189b6-966f-4c9c-b672-5f3f17c6ed0f', 'При каких условиях футболисты играют красным или желтым мячом?', 'При каких условиях футболисты играют красным или желтым мячом?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('c1b9b46f-ad3d-4959-9f8c-119d1b336187', TRUE, 'Во время неблагоприятных погодных условий – дождь, снег, туман', 'e6a189b6-966f-4c9c-b672-5f3f17c6ed0f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('3dea0e56-716f-4552-88ef-5266cb3b1f3b', FALSE, 'Если мяч вылетел за пределы поля', 'e6a189b6-966f-4c9c-b672-5f3f17c6ed0f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('412a240a-fde2-45ae-966a-cad417a05b58', FALSE, 'Когда начинается дополнительное время игры', 'e6a189b6-966f-4c9c-b672-5f3f17c6ed0f');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1dc1b6a2-3c58-46c8-8a4e-ecec012e62dc', FALSE, 'Во втором тайме', 'e6a189b6-966f-4c9c-b672-5f3f17c6ed0f');

-- 18 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('a3e5ada0-c8bf-42ea-b4b0-1f2a233245ba', 'Какое количество рефери необходимо для судейства одного футбольного матча?', 'Какое количество рефери необходимо для судейства одного футбольного матча?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('891a92b5-dc45-4ba1-a1cd-c500f91c4cc5', FALSE, 'Семеро', 'a3e5ada0-c8bf-42ea-b4b0-1f2a233245ba');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('b891616d-fe9d-4fbc-aeb7-4149e66e2ab7', FALSE, 'Пятеро', 'a3e5ada0-c8bf-42ea-b4b0-1f2a233245ba');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('39dbdf6c-c521-4b7c-b151-8c451ad5fd16', FALSE, 'Двое', 'a3e5ada0-c8bf-42ea-b4b0-1f2a233245ba');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('721a7559-d9c0-4a58-8978-2d6182f74f01', TRUE, 'Трое', 'a3e5ada0-c8bf-42ea-b4b0-1f2a233245ba');

-- 19 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('9459d58f-605c-47ae-a3ba-3c6342ec8adf', 'Кто является автором самого сильного удара в футболе, согласно данным на начало 2020 года?', 'Кто является атвором самого сильного удара в футболе, согласно данным на начало 2020 года?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('9e804ca9-8cb0-4d76-b8dd-55be6a74438e', TRUE, 'Халк', '9459d58f-605c-47ae-a3ba-3c6342ec8adf');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1fcd1534-855a-425f-979e-de5facc3ca8c', FALSE, 'Дэвид Бекхэм', '9459d58f-605c-47ae-a3ba-3c6342ec8adf');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('22031abc-919c-4cfe-ba56-0876db73a25b', FALSE, 'Лукас Подольски', '9459d58f-605c-47ae-a3ba-3c6342ec8adf');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('bdb83b07-87da-413e-aa3b-e03665c4263d', FALSE, 'Роберто Карлос', '9459d58f-605c-47ae-a3ba-3c6342ec8adf');

-- 20 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('22fbb077-02e6-45f6-a6c9-e151990538d8', 'Сколько желтых карточек допускается получить игроку за одну игру?', 'Сколько желтых карточек допускается получить игроку за одну игру?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('b3c60b4b-816b-482d-8b7e-91f24cc565c6', FALSE, 'Ни одной', '22fbb077-02e6-45f6-a6c9-e151990538d8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('8ed8e247-8cd3-47b2-997c-e8a2a81bb605', TRUE, 'Одну', '22fbb077-02e6-45f6-a6c9-e151990538d8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('218bac09-0831-4fc5-99ed-c1b829046c60', FALSE, 'Две', '22fbb077-02e6-45f6-a6c9-e151990538d8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('ab7e4d2a-1239-4876-b204-8e505fd6e226', FALSE, 'Три', '22fbb077-02e6-45f6-a6c9-e151990538d8');

-- 21 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('ce22cc72-3a62-49d2-a305-19d752063ac2', 'Какие цвета карточек существуют в футболе?', 'Какие цвета карточек существуют в футболе?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('ee8fe612-412c-44d6-a31d-180231c826bd', FALSE, 'Черная и красная', 'ce22cc72-3a62-49d2-a305-19d752063ac2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('d02b081e-85c3-431d-abad-9f26eec9beb1', FALSE, 'Красная и желтая', 'ce22cc72-3a62-49d2-a305-19d752063ac2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('7667639a-adcd-41e5-9de0-04db1fc69cd8', TRUE, 'Зеленая, красная и желтая', 'ce22cc72-3a62-49d2-a305-19d752063ac2');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('5c6c7c2b-dbd3-4998-98d0-26c368a15d34', FALSE, 'Синяя и красная', 'ce22cc72-3a62-49d2-a305-19d752063ac2');

-- 22 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('1cd64fe0-cfc7-4fa6-8442-0549cedbf2c8', 'Как называют игрока, который в соревновании забил больше всех голов?', 'Как называют игрока, который в соревновании забил больше всех голов?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('9c87db9f-e2db-440b-8b0e-7b5923a6829a', FALSE, 'Голкипер', '1cd64fe0-cfc7-4fa6-8442-0549cedbf2c8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1dca3c7a-81f5-4335-9b89-29435abc292c', FALSE, 'Вице-чемпион', '1cd64fe0-cfc7-4fa6-8442-0549cedbf2c8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('dfbab96b-91c7-4481-b29f-c867b82f201b', FALSE, 'Лидер', '1cd64fe0-cfc7-4fa6-8442-0549cedbf2c8');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('4cf98e0b-cb7b-42b3-b4c7-29c8338d754e', TRUE, 'Лучший бомбардир', '1cd64fe0-cfc7-4fa6-8442-0549cedbf2c8');

-- 23 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('d27869f2-480c-4c36-81a5-1ebc50940038', 'Чем сигнализирует нарушение боковой судья основному арбитру?', 'Чем сигнализирует нарушение боковой судья основному арбитру?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('11a82c1d-c925-42a2-aa93-5287bf22e824', TRUE, 'Флагом', 'd27869f2-480c-4c36-81a5-1ebc50940038');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('898c51fe-1e95-490a-a327-8a5f5dc0f49d', FALSE, 'Рукой', 'd27869f2-480c-4c36-81a5-1ebc50940038');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('79f8e52d-16a8-47e6-9865-5daaefd95b36', FALSE, 'Свистком', 'd27869f2-480c-4c36-81a5-1ebc50940038');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('af25f760-db20-4b69-994a-99fe1604a1c1', FALSE, 'Карточкой', 'd27869f2-480c-4c36-81a5-1ebc50940038');

-- 24 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('d0522b21-a2d8-415c-b8ce-d0f56a526b6b', 'Как называется штрафной удар, который совершают по воротам на расстоянии 11 метров?', 'Как называется штрафной удар, который совершают по воротам на расстоянии 11 метров?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('7051389f-793c-4950-b7fc-df3ad144522e', FALSE, 'Аут', 'd0522b21-a2d8-415c-b8ce-d0f56a526b6b');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('23b24c0f-a330-4be6-8b77-6b50423c344b', TRUE, 'Пенальти', 'd0522b21-a2d8-415c-b8ce-d0f56a526b6b');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('6e7ae698-5877-4c0d-9d3d-b5025875e97d', FALSE, 'Гол', 'd0522b21-a2d8-415c-b8ce-d0f56a526b6b');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('1d8790db-e7f3-48cb-b45b-dcff9e49dead', FALSE, 'Буллит', 'd0522b21-a2d8-415c-b8ce-d0f56a526b6b');

-- 25 вопрос
INSERT INTO questions(id, description, title, category_id, game_id, level_id, photo)
VALUES ('2f78d7e2-88e0-4dd8-8356-48ff525a6db5', 'Что в переводе с английского означает слово «футбол»?', 'Что в переводе с английского означает слово «футбол»?',
        'ed677e4d-ce26-4e75-8a9b-1be83b5b39d7', '716fb029-148d-4515-8aca-7cb8a604ee24', 'fdecef96-6ee3-4d25-803b-26042cb532a7', NULL);

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('fca496d3-fc36-4032-957a-036d75f1a0e0', FALSE, '«Мяч для ноги»', '2f78d7e2-88e0-4dd8-8356-48ff525a6db5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('c4725918-8779-4bf4-b3e4-d16290226592', FALSE, '«Бей ногой»', '2f78d7e2-88e0-4dd8-8356-48ff525a6db5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('2d88a38c-0cff-40b4-aa1b-f1d2555a55dd', FALSE, '«Ручной мяч»', '2f78d7e2-88e0-4dd8-8356-48ff525a6db5');

INSERT INTO answers(id, answer_is_right, title, question_id)
VALUES('e919b6a7-4c8f-415c-9042-ac6a7ffb1a51', TRUE, '«Мячом и ступней»', '2f78d7e2-88e0-4dd8-8356-48ff525a6db5');