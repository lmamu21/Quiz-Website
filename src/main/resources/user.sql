USE quiz;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id int,
    username varchar(255),
    password_hashed varchar(40)
)

