CREATE TABLE achievement (
    achievement_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    achievement_type INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);