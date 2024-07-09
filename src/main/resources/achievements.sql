CREATE TABLE achievements (
    achievement_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    achievement_type INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);