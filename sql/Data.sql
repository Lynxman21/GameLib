-- Publishers
INSERT INTO publishers (company_name) VALUES
('Ubifrost'),
('Electro Arts');

-- Categories
INSERT INTO categories (category_name) VALUES
('Actionventure'),
('Roleplay Saga'),
('Puzzlecraft');

-- Games
INSERT INTO games (name, publisher_id, release_date) VALUES
('Assassin’s Greed', 1, '2013-11-15'),
('Far Yawn', 1, '2012-03-27'),
('Rayman: Legends of Sleep', 1, '2014-08-29'),
('Need for Speedrun', 2, '2015-11-03'),
('The Sims: Survival', 2, '2016-02-04'),
('Battlefield: Quiet', 2, '2018-10-19');

-- Game Copies
INSERT INTO game_copies (game_id) VALUES
(1), (1), (1),
(2), (2), (2),
(3), (3), (3),
(4), (4), (4),
(5), (5), (5),
(6), (6), (6);

-- Category-Game Links
INSERT INTO category_game_link (category_id, game_id) VALUES
(1, 1), (2, 1),      -- Assassin’s Greed: Actionventure, Roleplay Saga
(2, 2), (3, 2),      -- Far Yawn: Roleplay Saga, Puzzlecraft
(3, 3), (1, 3),      -- Rayman: Legends of Sleep: Puzzlecraft, Actionventure
(1, 4), (2, 4),      -- Need for Speedrun: Actionventure, Roleplay Saga
(2, 5), (3, 5),      -- The Sims: Survival: Roleplay Saga, Puzzlecraft
(3, 6), (1, 6);      -- Battlefield: Quiet: Puzzlecraft, Actionventure

-- Member for test
INSERT INTO members (first_name, last_name, email, street, house_number, postal_code, city, country)
VALUES ('John', 'Doe', 'john.doe@example.com', 'Main Street', '1A', '00-001', 'Warsaw', 'Polska');