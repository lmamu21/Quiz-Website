DROP TABLE IF EXISTS messages;

CREATE TABLE Messages (
                          MessageID INT PRIMARY KEY AUTO_INCREMENT,
                          SenderID INT NOT NULL,
                          RecipientID INT NOT NULL,
                          MessageType ENUM('FriendRequest', 'Challenge', 'Note') NOT NULL,
                          Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- Fields specific to FriendRequest
                          FriendRequestStatus ENUM('Pending', 'Accepted', 'Declined') DEFAULT NULL,
    -- Fields specific to Challenge
                          QuizLink VARCHAR(255) DEFAULT NULL,
                          ChallengerBestScore INT DEFAULT NULL,
    -- Fields specific to Note
                          NoteText TEXT DEFAULT NULL,
                          FOREIGN KEY (SenderID) REFERENCES users(user_id),
                          FOREIGN KEY (RecipientID) REFERENCES user(user_id)
);