INSERT INTO worker (name, birthday, level, salary) VALUES
('John Doe', '1990-01-01', 'Trainee', 500),
('Jane Doe', '1992-05-15', 'Junior', 1500),
('Peter Parker', '1985-08-10', 'Middle', 3500),
('Tony Stark', '1970-05-29', 'Senior', 10000),
('Bruce Wayne', '1980-02-19', 'Senior', 9000),
('Clark Kent', '1988-06-18', 'Middle', 4000),
('Diana Prince', '1991-03-22', 'Junior', 1200),
('Barry Allen', '1995-11-25', 'Trainee', 600),
('Arthur Curry', '1984-01-29', 'Middle', 3800),
('Victor Stone', '1994-08-15', 'Trainee', 700);

INSERT INTO client (name) VALUES
('Wayne Enterprises'),
('Stark Industries'),
('LexCorp'),
('Daily Planet'),
('Queen Consolidated');

INSERT INTO project (client_id, start_date, finish_date) VALUES
(1, '2020-01-01', '2021-01-01'),
(1, '2021-03-15', '2022-03-15'),
(2, '2019-05-10', '2020-10-10'),
(3, '2022-01-01', '2023-06-01'),
(3, '2022-06-01', '2023-12-01'),
(3, '2023-01-01', '2023-08-01'),
(4, '2020-11-01', '2021-11-01'),
(4, '2021-12-01', '2022-12-01'),
(5, '2018-01-01', '2019-01-01'),
(5, '2019-02-01', '2020-02-01');

INSERT INTO project_worker (project_id, worker_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5),
(3, 1), (3, 6), (3, 7),
(4, 8), (4, 9), (4, 10),
(5, 5), (5, 6),
(6, 1), (6, 4),
(7, 2), (7, 3), (7, 8),
(8, 9), (8, 10),
(9, 7),
(10, 5), (10, 6);
