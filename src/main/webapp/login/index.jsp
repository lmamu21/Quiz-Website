<%
    HttpSession sess = request.getSession();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>#სახელი</title>
    <link rel="stylesheet" href="login/login.css">
    <link rel="stylesheet" href="login.css">

</head>
<body>
<div class="parent-container">
    <div class="icon-container">
        <img src="question.png" alt="User Icon">
    </div>
    <div class="main-label-container">
        <%
            if(sess != null && sess.getAttribute("loginStatus") != null){
                if((String) sess.getAttribute("loginStatus") != "loggedIn")
                    out.println("<h2> " + (String) sess.getAttribute("loginStatus") + "</h2>");
                else
                    out.println("<h2> " + (String) sess.getAttribute("loginStatus") + " as " + sess.getAttribute("username") + "</h2>");
            }else{
                out.println("<h2>Sign in to #სახელი</h2>");
            }
        %>
    </div>
    <div class="login-container">
        <form action="Login" method="post">
            <input name="username" type="text" placeholder="username" required>
            <input name="password" type="password" placeholder="password" required>
            <button type="submit" class="btn login-btn">Login</button>
            <button type="button" class="btn create-btn">Create Account</button>
        </form>
    </div>
</div>
</body>
</html>
