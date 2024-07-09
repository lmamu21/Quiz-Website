DROP TABLE IF EXISTS multiple_choice_answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id int AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password_hashed varchar(40) NOT NULL,
    salt varchar(40) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (username)
);

CREATE TABLE friends (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    friendship_status varchar(50) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CHECK (user_id != friend_id)
);




CREATE TABLE questions (
    question_id INT NOT NULL,
    question_text VARCHAR(255) NOT NULL,
    question_num INT,
    quiz_id INT NOT NULL,
    image_url varchar(255),
    multiple_choice BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (question_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

CREATE TABLE multiple_choice_answers (
    option_char CHAR NOT NULL,
    answer_text varchar(255) NOT NULL,
    question_id INT NOT NULL,
    correct_answer BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);

CREATE TABLE QuizAttempts (
    AttemptID SERIAL PRIMARY KEY,
    QuizID INT NOT NULL,
    UserID INT NOT NULL,
    AttemptDate TIMESTAMP NOT NULL DEFAULT NOW(),
    TimeTaken INT NOT NULL,
    PercentCorrect DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Table to store summary statistics for each quiz
CREATE TABLE PerformanceStats (
    StatID SERIAL PRIMARY KEY,
    QuizID INT NOT NULL,
    AverageScore DECIMAL(5, 2) NOT NULL,
    TotalAttempts INT NOT NULL,
    FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID)
);
