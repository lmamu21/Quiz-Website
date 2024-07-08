
CREATE TABLE question_response (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_index INT,
    question VARCHAR(255),
    mark INT,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);


CREATE TABLE picture_response (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_index INT,
    img_url VARCHAR(255),
    mark INT,
    FOREIGN KEY (quiz_id) REFERENCES  quizzes(quiz_id)
);


CREATE TABLE multiple_choice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_index INT,
    question VARCHAR(255),
    mark INT,
    FOREIGN KEY (quiz_id) REFERENCES  quizzes(quiz_id)
);


CREATE TABLE multiple_choice_options (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    option_answer VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES multiple_choice(id)
);


CREATE TABLE fill_the_blank (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_index INT,
    question_head VARCHAR(255),
    question_tail VARCHAR(255),
    mark INT,
    FOREIGN KEY (quiz_id) REFERENCES  quizzes(quiz_id)
);


CREATE TABLE question_response_correct_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    correct_answer VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES question_response(id)
);

CREATE TABLE picture_response_correct_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    correct_answer VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES picture_response(id)

);
CREATE TABLE multiple_choice_correct_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    correct_answer VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES multiple_choice(id )
);
CREATE TABLE fill_the_blank_correct_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    correct_answer VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES fill_the_blank(id )
);