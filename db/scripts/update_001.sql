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