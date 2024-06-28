DROP TABLE IF EXISTS multiple_choice_answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
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

CREATE TABLE friends (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    friendship_status varchar(50) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CHECK (user_id != friend_id)
);


CREATE TABLE quizzes (
    quiz_id INT NOT NULL,
    quiz_name varchar(254) NOT NULL,
    quiz_description varchar(254),
    author_id INT NOT NULL,
    time_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    random_questions_option BOOLEAN DEFAULT FALSE,
    page_options varchar(63) DEFAULT 'one-page',
    immediate_correction BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (quiz_id),
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
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
)
