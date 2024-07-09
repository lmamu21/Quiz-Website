<%@ page import="java.util.ArrayList" %>
<%@ page import="Commons.Quiz" %>
<%@ page import="Commons.Interfaces.IQuestion" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz Results</title>
    <link rel="stylesheet" href="result/result.css">
</head>
<body>
<div class="container">
    <h1>Quiz Results</h1>
    <div class="user-info">
        <p><strong>User:</strong> <% out.println((String)session.getAttribute("username")); %></p>
        <p><strong>Score:</strong> 100 %</p>
        <p><strong>Points:</strong> 3 / 3</p>
        <p><strong>Duration:</strong> <%out.println((String)session.getAttribute("elapsedTime"));%></p>
    </div>
    <div class="answers">
        <h2>Answers</h2>
        <table>
            <thead>
            <tr>
                <td><div class="header-item">Question</div></td>
                <td><div class="header-item">Your Answer</div></td>
                <td><div class="header-item">Correct Answer</div></td>
            </tr>
            </thead>

            <tbody>
        <%
            ArrayList<String> answers = (ArrayList<String>)session.getAttribute("answers");
            Quiz quiz = (Quiz) session.getAttribute("quiz");
            ArrayList<IQuestion> questions = quiz.getQuestions();

            for(int i = 0; i<questions.size(); i++) {
                out.println("<tr>");
                out.println("<td>");
                out.print("\t"+i+"</td>");
                out.print("\t<td>"+answers.get(i)+"</td>");
                out.print("\t<td>");
                ArrayList<String> correctAnswers = questions.get(i).getCorrectAnswers();
                for(String answer : correctAnswers) {
                    out.print("<p>"+answer+"</p>");
                }
                out.println("</td>");
                out.println("</tr>");
            }
        %>
            </tbody>
        </table>

    </div>
    <div>
        <form action="homepage" method="get">
            <div class="btn">
                <button type="submit" class="button">back to homepage</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
