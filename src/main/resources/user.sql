DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id int AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password_hashed varchar(40) NOT NULL,
    salt varchar(16) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (username)
)

