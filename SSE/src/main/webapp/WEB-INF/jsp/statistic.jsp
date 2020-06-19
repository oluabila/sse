<html>
<head>
    <title> Application</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Books</h2></caption>
        <tr>
            <th>Sex</th>
            <th>Nationality</th>


        </tr>
        <c:forEach var="studentsList" items="${studentsList}">
            <tr>
                <td><c:out value="${studentsList.username}" /></td>
                <td><c:out value="${studentsList.password}" /></td>


            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>