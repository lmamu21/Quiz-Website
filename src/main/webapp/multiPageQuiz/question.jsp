

<%
   // Question question = (Question) request.getAttribute("question");
    int currentIndex = (Integer) request.getSession().getAttribute("currentIndex");
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    IQuestion question = quiz.getQuestions().get(currentIndex);
    int totalQuestions = quiz.getQuestions().size();
    boolean showingMark = session.getAttribute("showingMark") != null;
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Commons.Answer" %>
<%@ page import="java.util.List" %>
<%@ page import="Commons.Interfaces.IQuestion" %>
<%@ page import="Commons.Quiz" %>
<%@ page import="Commons.Questions.FillTheBlankQuestion" %>
<%@ page import="Commons.Questions.MultipleChoiceQuestion" %>

<html>
<head>
    <title>Quiz Question</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="multiPageQuiz/question.css">
</head>
<body>

<div class="parent-container">
    <div class="quiz-header">
        <h1 class="quiz-title">Question <%= currentIndex + 1 %> of <%= totalQuestions %></h1>
    </div>

    <form action="multiplePageQuiz" method="post">
        <div class="quiz-questions-container">
            <fieldset>
                <div class="quiz-question">
                    <%
                        out.println(question.getHtmlComponent());
                    %>
                </div>
            </fieldset>
        </div>
        <br>
        <%
            if(currentIndex!=totalQuestions-1){
                if(showingMark){
                    out.println("<label>Mark: "+session.getAttribute("mark")+"/"+question.getMark());
                    out.println("<input type='submit' name='next' value='Next'>");
                }else{
                    out.println("<input type='submit' name='submit' value='Submit'>");
                }
            }
        %>
        <input type="submit" name="finish" value="Finish Quiz">
    </form>
</div>

</body>
</html>