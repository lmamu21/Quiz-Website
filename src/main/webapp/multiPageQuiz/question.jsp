

<%
   // Question question = (Question) request.getAttribute("question");
    int currentIndex = (Integer) request.getSession().getAttribute("currentIndex");

    ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("questions");
    int totalQuestions = questions.size();
    Question question = questions.get(currentIndex);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Commons.Question" %>
<%@ page import="Commons.Answer" %>
<%@ page import="java.util.List" %>
  <%@ page import="java.lang.reflect.Array" %>
  <%@ page import="java.util.ArrayList" %>

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

    <form action="multiPageQuiz" method="post">
        <div class="quiz-questions-container">
            <fieldset>
                <div class="quiz-question">
                    <legend><%= question.getQuestion() %></legend>
                    <% if ("PICTURE_RESPONSE".equals(question.getQuestionType())) { %>
                    <img src="<%= question.getUrl() %>" alt="Question Image">
                    <% } %>
                </div>

                <%
                    String questionType = String.valueOf(question.getQuestionType());
                    if ("QUESTION_RESPONSE".equals(questionType) || "FILL_IN_THE_BLANK".equals(questionType) || "PICTURE_RESPONSE".equals(questionType)) {
                %>
                <input type="text" name="response" required>
                <% } else if (Question.QuestionType.MULTIPLE_CHOICE.equals(question.getQuestionType())) { %>
                <%
                    List<Answer> choices = question.getAnswers();
                    if (choices != null && !choices.isEmpty()) {
                        for (Answer choice : choices) {
                %>
                <label>
                    <input type="radio" name="response" value="<%= choice.getAnswer() %>" required>
                    <%= choice.getAnswer() %>
                </label>
                <br>
                <%
                        }
                    } else {
                        out.println("<p>No answers available for this question.</p>");
                    }
                %>
                <% } %>
            </fieldset>
        </div>
        <br>
        <input type="submit" value="Next">
    </form>
</div>

</body>
</html>