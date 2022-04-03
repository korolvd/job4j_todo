CREATE TABLE IF NOT EXISTS users
(
  id SERIAL PRIMARY KEY,
  email VARCHAR NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS item
(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN,
    user_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS category
(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO category(name) VALUES ('Общее');
INSERT INTO category(name) VALUES ('Образование');
INSERT INTO category(name) VALUES ('Хобби');
INSERT INTO category(name) VALUES ('Путешествия');
INSERT INTO category(name) VALUES ('Дом');
INSERT INTO category(name) VALUES ('Работа');