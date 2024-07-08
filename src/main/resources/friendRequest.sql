CREATE TABLE friend_request (
    id INT AUTO_INCREMENT PRIMARY KEY,
    from_username VARCHAR(255) NOT NULL,
    to_username VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL
);