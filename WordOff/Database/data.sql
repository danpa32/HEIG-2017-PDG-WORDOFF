USE `WordOff`;

SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO lang_set (name) VALUES
  ('fr'),
  ('en');

-- Jocker
INSERT INTO letter (value, score) VALUES
  ('#', 0);

-- Français
-- Indivual letters
INSERT INTO letter (value, score, lang_set_id) VALUES
  ('A', 1, 1),
  ('B', 3, 1),
  ('C', 3, 1),
  ('D', 2, 1),
  ('E', 1, 1),
  ('F', 4, 1),
  ('G', 2, 1),
  ('H', 4, 1),
  ('I', 1, 1),
  ('J', 8, 1),
  ('K', 10, 1),
  ('L', 1, 1),
  ('M', 2, 1),
  ('N', 1, 1),
  ('O', 1, 1),
  ('P', 3, 1),
  ('Q', 8, 1),
  ('R', 1, 1),
  ('S', 1, 1),
  ('T', 1, 1),
  ('U', 1, 1),
  ('V', 4, 1),
  ('W', 10, 1),
  ('X', 10, 1),
  ('Y', 10, 1),
  ('Z', 10, 1);

-- tile of letter occurences
INSERT INTO tile (letter_id, lang_set_id) VALUES
  (1, 1), (1, 1), -- 2x Jocker
  (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), (2, 1), -- 13x A
  (3, 1), (3, 1), -- 2x B
  (4, 1), (4, 1), (4, 1), (4, 1), -- 4x C
  (5, 1), (5, 1), -- 2x D
  (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), (6, 1), -- 17x E
  (7, 1), (7, 1), (7, 1), -- 3x F
  (8, 1), (8, 1), -- 2x G
  (9, 1), -- 1x H
  (10, 1), (10, 1), (10, 1), (10, 1), (10, 1), (10, 1), (10, 1), (10, 1), (10, 1), -- 9x I
  (11, 1), -- 1x J
  (12, 1), -- 1x K
  (13, 1), (13, 1), (13, 1), (13, 1), -- 4x L
  (14, 1), (14, 1), (14, 1), (14, 1), -- 4x M
  (15, 1), (15, 1), (15, 1), (15, 1), (15, 1), -- 5x N
  (16, 1), (16, 1), (16, 1), (16, 1), (16, 1), (16, 1), (16, 1), -- 7x O
  (17, 1), (17, 1), (17, 1), (17, 1), -- 4x P
  (18, 1), (18, 1), -- 2x Q
  (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), -- 9x R
  (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), -- 9x S
  (21, 1), (21, 1), (21, 1), (21, 1), (21, 1), (21, 1), (21, 1), -- 7x T
  (22, 1), (22, 1), (22, 1), (22, 1), (22, 1), (22, 1), -- 6x U
  (23, 1), -- 1x V
  (24, 1), -- 1x W
  (25, 1), -- 1x X
  (26, 1), -- 1x Y
  (27, 1); -- 1x Z

-- English
-- Indivual letters
INSERT INTO letter (value, score, lang_set_id) VALUES
  ('A', 1, 2),
  ('B', 3, 2),
  ('C', 3, 2),
  ('D', 2, 2),
  ('E', 1, 2),
  ('F', 4, 2),
  ('G', 2, 2),
  ('H', 4, 2),
  ('I', 1, 2),
  ('J', 8, 2),
  ('K', 5, 2),
  ('L', 1, 2),
  ('M', 3, 2),
  ('N', 1, 2),
  ('O', 1, 2),
  ('P', 3, 2),
  ('Q', 10, 2),
  ('R', 1, 2),
  ('S', 1, 2),
  ('T', 1, 2),
  ('U', 1, 2),
  ('V', 4, 2),
  ('W', 4, 2),
  ('X', 8, 2),
  ('Y', 4, 2),
  ('Z', 10, 2);

-- tile of letter occurences
INSERT INTO tile (letter_id, lang_set_id) VALUES
  (1, 2), (1, 2), -- 2x Jocker
  (28, 2), (28, 2), (28, 2), (28, 2), (28, 2), (28, 2), (28, 2), -- 8x A
  (29, 2), (29, 2), -- 2 B
  (30, 2), (30, 2), (30, 2), -- 3x C
  (31, 2), (31, 2), (31, 2), (31, 2), -- 4x D
  (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), (32, 2), -- 12x E
  (33, 2), (33, 2), -- 2x F
  (34, 2), (34, 2), (34, 2), -- 3x G
  (35, 2), (35, 2), (35, 2), -- 3x H
  (36, 2), (36, 2), (36, 2), (36, 2), (36, 2), (36, 2), (36, 2), -- 7x I
  (37, 2), -- 1x J
  (38, 2), (38, 2), -- 2x K
  (39, 2), (39, 2), (39, 2), (39, 2), (39, 2), -- 5x L
  (40, 2), (40, 2), (40, 2), -- 3x M
  (41, 2), (41, 2), (41, 2), (41, 2), (41, 2), (41, 2), -- 6x N
  (42, 2), (42, 2), (42, 2), (42, 2), (42, 2), (42, 2), -- 6x O
  (43, 2), (43, 2), (43, 2), -- 3x P
  (44, 2), -- 1x Q
  (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), -- 7x R
  (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), -- 9x S
  (47, 2), (47, 2), (47, 2), (47, 2), (47, 2), -- 5x T
  (48, 2), (48, 2), (48, 2), (48, 2), -- 4x U
  (49, 2), -- 1x V
  (50, 2), -- 1x W
  (51, 2), -- 1x X
  (52, 2), (52, 2), -- 2x Y
  (53, 2); -- 1x Z


-- AI player has first id
INSERT INTO player (id, name) VALUES
  (1, 'AI');

INSERT INTO ai (id) VALUES
  (1);

-- Test users
INSERT INTO player (id, name) VALUES
  (2, 'one'), (3, 'two'), (4, 'three');
INSERT INTO user (id, level, login, password, coins) VALUES
  (2, 1, 'one', 'pass', 50), (3, 1, 'two', 'pass', 50), (4, 1, 'three', 'pass', 50);
INSERT INTO relation (owner_id, target_id, status) VALUES
  (2, 3, 0), (3, 2, 0), (2, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;