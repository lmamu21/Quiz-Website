<%@ page import="Commons.Quiz" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="Commons.Interfaces.IQuestion" %>
<%@ page import="Commons.Questions.FillTheBlankQuestion" %>
<%@ page import="Commons.Questions.MultipleChoiceQuestion" %>
<%@ page import="Commons.Questions.PictureResponseQuestion" %>
<%@ page import="Commons.Questions.QuestionResponseQuestion" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: velija21
  Date: 25.06.24
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Multiple question quiz</title>
    <link rel="stylesheet" href="singlePage.css">
    <link rel="stylesheet" href="singlePageQuiz/singlePage.css">
</head>
<body>
    <header>
        <h1>Quiz Name</h1>
        <p>description : this is the quiz description</p>
    </header>
    <main>
        <form action="/Quiz_Web_war/singlePageQuiz" method="post">


            <%

                ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
               Quiz quiz = (Quiz)session.getAttribute("quiz");
               questions = quiz.getQuestions();

                for(int i = 0 ; i < questions.size() ; i++){
                    out.print(questions.get(i).getHtmlComponent());
                }
            %>

            <section class = "submit_section">
                <button type="button" class="btn quit-btn">Quit</button>
                <button type="submit" class="btn submit-btn">Submit</button>
            </section>

        </form>
    </main>
</body>
</html>
