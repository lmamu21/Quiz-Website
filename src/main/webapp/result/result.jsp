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
        <p><strong>User:</strong> #სახელი</p>
        <p><strong>Score:</strong> 100 %</p>
        <p><strong>Points:</strong> 3 / 3</p>
        <p><strong>Duration:</strong> 7 min 21s</p>
    </div>
    <div class="answers">
        <h2>Answers</h2>
        <div class="answer header">
            <div class="header-item">Question</div>
            <div class="header-item">Your Answer</div>
            <div class="header-item">Correct Answer</div>
        </div>
        <%
            ArrayList<String> answers = (ArrayList<String>)session.getAttribute("answers");
            Quiz quiz = (Quiz) session.getAttribute("quiz");
            ArrayList<IQuestion> questions = quiz.getQuestions();

            for(int i = 0; i<questions.size(); i++) {
                out.println("<div class='answer'>");
                out.print("\t<div class='item'>");
                out.println(i+"</div>");
                out.print("\t<div class='item'>");
                out.println(answers.get(i)+"</div>");
                out.print("\t<div class='item'>");
                ArrayList<String> correctAnswers = questions.get(i).getCorrectAnswers();
                for(String answer : correctAnswers) {
                    out.print("<span>"+answer+"</span>");
                }
                out.println("</div>");
            }
        %>

        <div class="answer">
            <div class="item">#შეკითხვა1#?</div>
            <div class="item">#პასუხი1#</div>
            <div class="item">#სწორი პასუხი1#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა2#?</div>
            <div class="item">#პასუხი2#</div>
            <div class="item">#სწორი პასუხი2#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა3#?</div>
            <div class="item">#პასუხი3#</div>
            <div class="item">#სწორი პასუხი3#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა1#?</div>
            <div class="item">#პასუხი1#</div>
            <div class="item">#სწორი პასუხი1#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა2#?</div>
            <div class="item">#პასუხი2#</div>
            <div class="item">#სწორი პასუხი2#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა3#?</div>
            <div class="item">#პასუხი3#</div>
            <div class="item">#სწორი პასუხი3#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა1#?</div>
            <div class="item">#პასუხი1#</div>
            <div class="item">#სწორი პასუხი1#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა2#?</div>
            <div class="item">#პასუხი2#</div>
            <div class="item">#სწორი პასუხი2#</div>
        </div>
        <div class="answer">
            <div class="item">#შეკითხვა3#?</div>
            <div class="item">#პასუხი3#</div>
            <div class="item">#სწორი პასუხი3#</div>
        </div>
    </div>
</div>
</body>
</html>
