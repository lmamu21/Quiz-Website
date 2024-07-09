<%@ page import="java.util.ArrayList" %>
<%@ page import="Commons.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz History</title>
    <link rel="stylesheet" href="history/history.css">
</head>
<body>
<header>
    <h1>Quiz History</h1>
</header>
<main>
    <section class="quiz-history">
        <h2>Your Quiz Performances</h2>
        <div class="quiz">
            <h3>Quiz 1: Quiz name</h3>
            <div class="performance">
                <div class="date">2024-06-01</div>
                <div class="score">85%</div>
            </div>
            <div class="performance">
                <div class="date">2024-05-15</div>
                <div class="score">90%</div>
            </div>
            <div class="performance">
                <div class="date">2024-06-15</div>
                <div class="score">90%</div>
            </div>
        </div>
        <div class="quiz">
            <h3>Quiz 2: Quiz name</h3>
            <div class="performance">
                <div class="date">2024-06-02</div>
                <div class="score">80%</div>
            </div>
            <div class="performance">
                <div class="date">2024-05-18</div>
                <div class="score">85%</div>
            </div>
        </div>
        <%
            String username= (String) session.getAttribute("username");
            AccountManager manager = (AccountManager) application.getAttribute("AccountManager");
            QuizManager quizManager = (QuizManager) application.getAttribute("QuizManager");

            int user_id = Integer.parseInt(manager.getID(username));
            SummaryPageService service = (SummaryPageService) application.getAttribute("SummaryPageService");
            ArrayList< QuizAttempt> attempts = service.getUsersAttempts(user_id);
            
            for(QuizAttempt attempt : attempts) {
                Quiz quiz = quizManager.getQuiz(attempt.getQuizId());
                out.println("<div class='quiz'>");
                out.println("<h3>"+quiz.getQuizName()+"</h3>");
                out.println("<div class='performance'>");
                out.println("<div class=\"date\">"+ attempt.getAttemptDate()+ "</div>");
                out.println("<div class=\"score\">" + attempt.getPercentCorrect() + "</div>");
                out.println("</div>");
            }

        %>
    </section>
</main>
</body>
</html>