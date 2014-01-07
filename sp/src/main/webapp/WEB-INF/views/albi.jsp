<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  <%String m = "ciao"; %>
</h1>

<P><%=m %>  The time on the server is ${serverTime}. </P>
</body>
</html>
