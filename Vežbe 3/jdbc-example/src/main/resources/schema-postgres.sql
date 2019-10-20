DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) DEFAULT NULL
);
