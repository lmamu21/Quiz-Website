<%
    HttpSession sess = request.getSession();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz-Website</title>
    <link rel="stylesheet" href="register/register.css">
</head>
<body>

<div class="parent-container">
    <div class="main-label-container">
        <h1>Sign Up</h1>
        <h3>It is quick and easy.</h3>
    </div>
    <div class="main-label-container">
        <%
            if(sess != null && sess.getAttribute("registerStatus")!=null){
                if( ((String)sess.getAttribute("registerStatus")).equals("no match")){
                    out.println("<h2>Passwords do not match!</h2>");
                    out.println("<h3>Please try again</h3>");
                }else if( ((String)sess.getAttribute("registerStatus")).equals("already used")){
                    out.println("<h2>Username is already used</h2");
                    out.println("<h3>Please try again</h3>");
                }
            }
        %>
    </div>
    <div class="register-container">
        <form action="register" method="post">
            <input name="username" type="text" placeholder="Enter your Username" required>
            <input name="password" type="password" placeholder="Enter your Password" required>
            <input name="repeat-password" type="password" placeholder="Repeat your Password" required>
<!--            <button type="submit" class="btn register-sbt">Login</button>-->
            <button type="submit" class="btn register-btn">Create Account</button>
        </form>
    </div>
</div>
</body>
</html>