<%@ page import="Commons.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Commons.Question" %>
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
        <form action="/singlePageQuiz" method="post">


            <%

                ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
                ArrayList<String> answers1 = new ArrayList<String>();
                answers1.add("1");
                questions.add(new FillTheBlankQuestion(1,"1 + " , " = 2" , answers1, 1));
                ArrayList<String> answers2 = new ArrayList<>();
                answers2.add("2");
                ArrayList<String> choices = new ArrayList<>();
                choices.add("1");
                choices.add("2");
                choices.add("3");
                questions.add(new MultipleChoiceQuestion(2,"1 + 1 = " , choices,answers2,1));
                ArrayList<String> answers3 = new ArrayList<>();
                answers2.add("3");
                questions.add(new PictureResponseQuestion(3 , "https://upload.wikimedia.org/wikipedia/commons/6/62/Eo_circle_red_number-3.svg" , answers3 , 1 ));
                questions.add(new QuestionResponseQuestion(4,"what is 2 + 2 " , Arrays.asList(new String[]{"4"}), 1));
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
