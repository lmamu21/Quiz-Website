
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Quiz</title>
    <link rel="stylesheet" href="create/create.css">
</head>
<body>
<div class="wrapper">
    <div class="form">
        <form id="quiz-creation-form" action="create" method="post">
            <div class="quiz-summary-wrapper">
                <label for="quiz-name">Quiz name: </label>
                <input type="text" name="quiz-name" id="quiz-name" placeholder="Name"><br>
                <label for="quiz-description">Quiz description: </label>
                <input type="text" id="quiz-description" name="quiz-description" placeholder="Description"><br>

                <fieldset>
                    <legend>Choose how to show questions:</legend>
                    <div class="radio-wrapper">
                        <div class="radio-option">
                            <input type="radio" id="one-page" name="page-option" value="one-page" checked>
                            <label for="one-page">Show all questions on one page</label>
                        </div>
                        <div class="radio-option">
                            <input type="radio" id="multiple-page" name="page-option" value="multiple-page">
                            <label for="multiple-page">Show each question on different page</label>
                        </div>
                    </div>
                </fieldset>

                <div class="checkbox-wrapper">
                    <input type="checkbox" id="random" name="random" value="random">
                    <label for="random">Randomize sequence of questions</label><br>
                </div>
            </div>

            <select name="question-type" id="question-type">
                <option value="multiple-choice question">Multiple Choice Question</option>
                <option value="question-response">Question Response</option>
                <option value="image-response question">Image Response Question</option>
                <option value="fill in the blank">Fill in the Blank</option>
            </select>
            <button class="btn" type="button" onclick="addQuestion()">Add Question</button>
            <div id="questions"></div>
            <input type="text" name="username" value="<%String username = (String) request.getSession().getAttribute("username"); out.println(username);%>" hidden>
            <input class="btn" type="submit">
        </form>
    </div>
</div>
<script src="create/create.js"></script>
</body>
</html>
