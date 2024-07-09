<%--
  Created by IntelliJ IDEA.
  User: luka
  Date: 09.07.24
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Commons.Quiz" %>
<%@ page import="Commons.QuizManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>allQuizzes</title>
    <link rel="stylesheet" href="popularQuizzes/popularQuizzes.css">
</head>

<body>
<header>
    <%
        HttpSession sess = request.getSession();
    %>
    <div>
        <p> user : <%= (String) sess.getAttribute("username")%></p>
    </div>
    <form action="/Quiz_Web_war/Logout" method="post">
        <button type="submit" class="btn ">Logout</button>
    </form>
    <form action="/Quiz_Web_war/homepage" method="get">
        <button type="submit" class="btn">homepageeeeee</button>
    </form>
</header>

<div class="main-content">
    <div class="header-container">
        <h1 class="header-title">Feed</h1>
    </div>
    <div class="popular quizzes">
        <div class="border-title">Quizzes</div>
        <table class="quizzes-table">
            <thead></thead>
            <tbody>
            <%
                QuizManager manager = (QuizManager) application.getAttribute("QuizManager");

                ArrayList<Quiz> recents = (ArrayList<Quiz>) manager.getQuizzes();

            %>

            <%
                for(Quiz q : recents) {
                    out.println("<tr><td><a href=\" " + "/Quiz_Web_war/SummaryPage?quizId="+q.getQuizID()+ "\"> "+q.getQuizName()+"</a></td></tr>");
                }
            %>
            </tbody>
        </table>
    </div>
</div>



</body>
</html>
