<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<html>
<body>
<!-- CommandName 'user' will bind the form parameter to the 'User' bean -->
<form action="/app-webapp-1.0.0-SNAPSHOT/mvc/submitData" method="post">
    <label path="name">Name</label><input type="text" name="name"/></br></br>
    <input type="submit" name="SUBMIT">
</form>
</body>
</html>