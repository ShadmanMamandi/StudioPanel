<%--
  Created by IntelliJ IDEA.
  User: shiva
  Date: 5/23/17
  Time: 11:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String ok = request.getParameter("ok");
%>


<form method="post">
    <ol>
        <li>
            <label>ok</label>
            <input name="ok"/>
        </li>
    </ol>

    <%=ok%>

</form>
</body>
</html>
