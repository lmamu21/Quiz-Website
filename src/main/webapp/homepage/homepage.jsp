
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: luka
  Date: 15.06.24
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Homepage</title>
    <link rel="stylesheet" href="homepage/homepage.css">
    <link rel="stylesheet" href="homepage.css">
</head>

<body>
    <header>
        <%
            HttpSession sess = request.getSession();
        %>
        <div>
            <p> user : <%= (String) sess.getAttribute("username")%></p>
        </div>
        <form action="Logout" method="post">
            <button type="submit" class="btn ">Logout</button>
        </form>
    </header>
    <div class="wrapper">

        <div class="sidebar left">
            <div class="popular quizzes">
                <div class="border-title">Popular quizzes</div>
                <table class="quizzes-table">
                    <thead></thead>
                    <tbody>


                    </tbody>
                </table>
            </div>
            <div class="recently-created quizzes">
                <div class="border-title">Recently created quizzes</div>
                <table class="quizzes-table">
                    <thead></thead>
                       <tbody>
                       <td><a> quiz #3 </a></td>
                       </tbody>
                    </table>
            </div>
        </div>

        <div class="main-content">
            <div class="header-container">
                <h1 class="header-title">Feed</h1>
                <div class="icons-container">
                    <div class="icon-wrapper">
                        <img src="message.svg" alt="message icon" class="icon" style=" filter: invert(100%)" >
                        <div class="hover-content">
                            <h4>Messages</h4>
                            <div class="message note">
                                <p><strong><a>USER #5</a></strong> Sent you note: Hello</p>
                            </div>
                            <div class="message note">
                                <p><strong><a>User1 #11</a></strong> sent you note: how are you?</p>
                            </div>

                        </div>
                    </div>
                    <div class="icon-wrapper">
                        <img src="friend-add.svg" alt="friend icon" class="icon">
                        <div class="hover-content">
                            <h4>Friend Requests</h4>

                            <div class="friend-request">
                                <p><strong><a>someone</a></strong> sent friend request</p>
                                <button class="friend-request-btn accept-button">Accept</button>
                                <button class="friend-request-btn reject-button">Decline</button>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
            <div class="feed">
                <div class="post announcement">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p>Welcome to our new quiz website! Check out the new features and enjoy taking quizzes.</p>
                        <p>Don't miss our upcoming quiz competition next month. Stay tuned for more details!</p>
                    </div>
                </div>

                <!-- Friend's Recent Activities Section -->
                <div class="post activity">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p><a class="link">John Doe</a> completed the quiz <a href="quiz-1.html" class="link">"History of Ancient Rome"</a> and scored 95%.</p>
                   </div>
                </div>

                <div class="post activity">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p><a class="link">Jane Smith</a> created a new quiz <a href="quiz-2.html" class="link">"World Capitals"</a>.</p>
                    </div>
                </div>
                <div class="post announcement">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci architecto at consequatur corporis dignissimos distinctio laudantium maiores maxime molestias nesciunt nihil nisi quas ratione rem reprehenderit sint suscipit ullam, veniam!.</p>
                    </div>
                </div>
                <div class="post activity">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p><a class="link">Alice Brown</a> earned the <a href="achievements.html" class="link">"Quiz Master"</a> achievement for completing 100 quizzes!</p>
                    </div>
                </div>

            </div>
        </div>

        <div class="sidebar right">
            <div class="quizzes">
                <div class="border-title">Recently taken quizzes</div>
                <table class="quizzes-table">
                    <thead></thead>
                    <tbody>
                    <tr><td>1</td>
                        <td><a>quiz #2</a></td></tr>
                    <tr><td>2</td>
                        <td><a>quiz #3</a></td></tr>
                    </tbody>
                </table>

                <button class="btn" value="Take quiz">Take another quiz</button>
            </div>

            <div class="quizzes">
                <div class="border-title">Recently created quizzes</div>
                <table class="quizzes-table">
                   <tr>
                       <td>1</td>
                       <td><a> quiz #3 </a></td>
                   </tr>
                    <tr>
                        <td>2</td>
                       <td><a> quiz #4 </a></td>
                    </tr>
                    <tr>
                        <td>3</td>
                       <td><a> quiz #1 </a></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td><a> quiz #0</a></td>
                    </tr>
                </table>
                <a href="/create"><button class="btn" value="Create quiz">Create quiz</button></a>
            </div>

            <div class="achievements">
                <div class="border-title">achievements</div>
                <div class="flex">
                    <div class="achievement">
                        <div class="label"></div>
                        <div style="color: cornflowerblue">hello</div>
                    </div>
                    <div class="achievement">
                        <div class="label"></div>
                        <div style="color: darkgreen">best quizzer</div>
                    </div>
                    <div class="achievement">
                        <div class="label"></div>
                        <div style="color: crimson ">lorem ipsum</div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</body>
</html>
