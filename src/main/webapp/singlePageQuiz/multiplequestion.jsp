<%--
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
    <link rel="stylesheet" href="multiplequestion.css">
    <link rel="stylesheet" href="multiplequestion/multiplequestion.css">
</head>
<body>
    <header>
        <h1>Quiz Name</h1>
        <p>description : this is the quiz description</p>
    </header>
    <main>
        <form action="/submit" method="post">
        <section class = "question_section">
            <div class = "question_num">
                <p>
                    1.
                </p>
            </div>
            <div class = "question_div">
                <img src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7FTJlg46D2I_oj3XexNUDlBOMqXNuGGMbcA&s">
            </div>

            <div class = "answer_div">

                    <label class = "select-answer">
                        <input type="radio" name="fruit" value="apple"> Apple
                    </label>
                    <label  class = "select-answer">
                        <input type="radio" name="fruit" value="banana"> Banana
                    </label>
                    <label  class = "select-answer">
                        <input type="radio" name="fruit" value="cherry"> Cherry
                    </label>
                    <label  class = "select-answer">
                        <input type="radio" name="fruit" value="date"> Date
                    </label >
                    <label  class = "select-answer">
                        <input type="radio" name="fruit" value="elderberry"> Elderberry
                    </label>
            </div>

        </section>

        <section class = "question_section">
            <div class = "question_num">
                <p>
                    2.
                </p>
            </div>
            <div class = "question_div">
                <p> ???????????????????????????????????????????????????? </p>
            </div>
            <div class = "answer_div">
                    <label class = "fill-in">
                        <input type="text" name="fruit" >
                    </label>
            </div>
        </section >
            <section class = "submit_section">
                <button type="button" class="btn quit-btn">Quit</button>
                <button type="submit" class="btn submit-btn">Submit</button>
            </section>

        </form>
    </main>
</body>
</html>
