<%
    HttpSession sess = request.getSession();
%>
<!DOCTYPE html>
<%--<html lang="en">--%>
<head>
    <meta charset="UTF-8">
    <title>Quiz-Website</title>
    <link rel="stylesheet" href="login/login.css">

</head>
<body>
<div class="parent-container">
    <div class="icon-container">
        <img src="login/question.png" alt="User Icon">
    </div>
    <div class="main-label-container">
        <%
            if(sess != null && sess.getAttribute("loginStatus") != null){
                if(!((String) sess.getAttribute("loginStatus")).equals( "loggedIn"))
                    out.println("<h2> " + (String) sess.getAttribute("loginStatus") + "</h2>");
                else
                    out.println("<h2> " + (String) sess.getAttribute("loginStatus") + " as " + sess.getAttribute("username") + "</h2>");
            }else{
                out.println("<h2>Sign in to Hat-Trick Brain Teasers\n</h2>");
            }
        %>
    </div>
    <div class="login-container">
        <form action="login" method="post">
            <input name="username" type="text" placeholder="username" required>
            <input name="password" type="password" placeholder="password" required>
            <button type="submit" class="btn login-btn">Login</button>
            <a href="register"><button type="button" class="btn create-btn">Create account</button></a>
        </form>
    </div>
</div>
</body>
</html>
