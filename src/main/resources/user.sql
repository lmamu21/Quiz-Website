DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id int AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password_hashed varchar(40) NOT NULL,
    salt varchar(16) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (username)
);

DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    friendship_status varchar(50) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CHECK (user_id != friend_id)
)
