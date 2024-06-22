<%--
  Created by IntelliJ IDEA.
  User: luka
  Date: 17.06.24
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Homepage</title>
    <link rel="stylesheet" href="summary.css">
</head>

<body>
    <!-- TODO
        1. hot link to creators user page
        2. show scores in tables
        3. summary statistics of all users
        4. start quiz in practice mode, if available
        5. edit quiz if user is owner
    -->
    <div class="wrapper">

        <div class="sidebar left">
            <div class="leaderboard all-time users">
                <div class="border-title">All time high scorers</div>
                <table class="users-table">
                    <thead></thead>
                    <tbody>
                        <tr><td>1</td><td><a>USER #1</a></td><td>49</td></tr>
                        <tr><td>2</td><td><a>USER #5</a></td><td>49</td></tr>
                        <tr><td>3</td><td><a>User #8</a></td><td>47</td></tr>
                    </tbody>
                </table>
            </div>

            <div class="leaderboard last-day users">
                <div class="border-title">Best scores in last 24 hours</div>
                <table class="users-table">
                    <thead></thead>
                    <tbody>
                        <tr><td>1</td><td><a>user #1</a></td><td>40</td></tr>
                        <tr><td>2</td><td><a>user #5</a></td><td>49</td></tr>
                        <tr><td>3</td><td><a>user #8</a></td><td>38</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="main-content">
            <div class="header-container">
                <h1 class="header-title">Quiz Summary</h1>
            </div>

            <div class="feed">
                <div class="post announcement">
                    <div class="stripe"></div>
                    <div class="post-content">
                        <p>Description of quiz</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consequuntur doloremque, ex fugiat iure minima nulla optio possimus qui voluptatibus. Dolore doloribus eius, error laborum minus nam nihil nobis reiciendis repellat?</p>
                    </div>
                </div>
            </div>
            <div class="footnote">
                <button class="btn">Start Quiz</button>
            </div>
        </div>

        <div class="sidebar right">
           <div class="users">
                <div class="border-title">Recent performances</div>
                <table class="users-table">
                    <tr>
                        <td>1</td>
                        <td><a> user #3 </a></td>
                        <td>10</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td><a> user #4 </a></td>
                        <td>17</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td><a> user #1 </a></td>
                        <td>39</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td><a> quiz #0</a></td>
                        <td>20</td>
                    </tr>
                </table>
                <button class="btn" value="Create quiz">Create quiz</button>
            </div>
        </div>
    </div>
</body>
</html>
