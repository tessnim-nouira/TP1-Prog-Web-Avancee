<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: octanet
  Date: 30/10/2026
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>L'heure actuelle est :
    <%!Date date= new Date();%>
    <%=date.toString()%>
</h2>
<h2>
    L'adresse IP de serveur est:
    <%=request.getRemoteHost()%>
</h2>
</body>
</html>
